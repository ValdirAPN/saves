package br.com.saves.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.saves.R
import br.com.saves.model.Bank
import br.com.saves.model.BankAccount
import br.com.saves.model.FinanceEntity
import br.com.saves.ui.theme.SavesTheme
import java.util.Locale

@Composable
fun FinanceInstitutionDropdown(
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit),
    items: List<FinanceEntity>,
    selectedItem: FinanceEntity? = null,
    onItemSelected: (index: Int, item: FinanceEntity) -> Unit,
    itemToString: @Composable (FinanceEntity) -> String = {
        when (it.name) {
            Bank.BANK_DEFAULT.name -> stringResource(id = R.string.wallet)
            else -> it.name.replaceFirstChar { firstChar -> firstChar.uppercase() }
        }
    },
    drawItem: @Composable (FinanceEntity, () -> Unit) -> Unit = { item, onClick ->
        val tint = item.institution.getIconTint()
        val iconTint = if (tint != null) Color(tint) else Color.Unspecified
        FinanceInstitutionDropdownItem(
            text = itemToString(item),
            icon = painterResource(id = item.institution.getIconRes()),
            iconTint = iconTint,
            iconContainer = Color(item.institution.getIconBackground()),
            onClick = onClick
        )
    }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.height(IntrinsicSize.Min), contentAlignment = Alignment.CenterStart) {
        SavesTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = if (selectedItem != null) null else placeholder,
            value = "",
            onValueChange = {},
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, null)
            },
            readOnly = true
        )

        selectedItem?.let { item ->
            val tint = item.institution.getIconTint()
            val iconTint = if (tint != null) Color(tint) else Color.Unspecified
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100f))
                        .size(32.dp)
                        .background(Color(item.institution.getIconBackground()))
                        .aspectRatio(1f)
                        .padding(8.dp),
                    painter = painterResource(id = item.institution.getIconRes()),
                    contentDescription = null,
                    tint = iconTint
                )
                Text(
                    text = itemToString(item),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable { expanded = true },
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
                    itemsIndexed(items) { index, item ->
                        drawItem(
                            item,
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
fun FinanceInstitutionDropdownItem(
    text: String,
    icon: Painter,
    iconTint: Color,
    iconContainer: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .size(32.dp)
                .background(iconContainer)
                .aspectRatio(1f)
                .padding(8.dp),
            painter = icon,
            contentDescription = null,
            tint = iconTint
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun FinanceInstitutionDropdownPreview() {
    SavesTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            FinanceInstitutionDropdown(
                items = BankAccount.fakeList(),
                onItemSelected = { _, _ -> },
                selectedItem = BankAccount.fakeList().first(),
                placeholder = {}
            )
        }
    }
}

@Preview
@Composable
fun FinanceInstitutionDropdownItemPreview() {
    val institution = Bank.NUBANK
    val institutionIconTint = institution.getIconTint()
    val tint = if (institutionIconTint != null) Color(institutionIconTint) else Color.Unspecified
    SavesTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            FinanceInstitutionDropdownItem(
                text = "Roxinho",
                icon = painterResource(id = institution.getIconRes()),
                iconTint = tint,
                iconContainer = Color(institution.getIconBackground()),
                onClick = {}
            )
        }
    }
}