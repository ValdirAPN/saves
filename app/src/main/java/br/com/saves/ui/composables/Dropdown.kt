package br.com.saves.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun <T> Dropdown(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable (() -> Unit),
    notSetLabel: String? = null,
    items: List<T>,
    selectedItem: T? = null,
    onItemSelected: (index: Int, item: T) -> Unit,
    itemToString: (T) -> String = { it.toString() },
    drawItem: @Composable (T, Boolean, () -> Unit) -> Unit = { item, itemEnabled, onClick ->
        DropdownItem(
            text = itemToString(item),
            enabled = itemEnabled,
            onClick = onClick
        )
    }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        SavesTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = placeholder,
            value = selectedItem?.let { itemToString(it) } ?: "",
            onValueChange = {},
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, "")
            },
            readOnly = true
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(enabled = enabled) { expanded = true },
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false }
        ) {
            Surface(
                modifier = Modifier.padding(vertical = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                val listState = rememberLazyListState()
                if (selectedItem.toString().isEmpty().not()) {
                    val index = items.indexOf(selectedItem)

                    if (index > -1) {
                        LaunchedEffect(key1 = "ScrollToSelected") {
                            listState.scrollToItem(index = index)
                        }
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    if (notSetLabel != null) {
                        item {
                            DropdownItem(
                                text = notSetLabel,
                                enabled = false,
                                onClick = { },
                            )
                        }
                    }
                    itemsIndexed(items) { index, item ->
                        drawItem(
                            item,
                            true
                        ) {
                            onItemSelected(index, item)
                            expanded = false
                        }

                        if (index < items.lastIndex) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun DropdownItem(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier
        .clickable(enabled) { onClick() }
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}