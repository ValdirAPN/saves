package br.com.saves.feature.transaction

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.ui.composables.DatePickerField
import br.com.saves.ui.composables.Dropdown
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.composables.SavesTopBar
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.NumberVisualTransformation
import br.com.saves.utils.getDateString

@Composable
fun ExpenseRoute(onBackPressed: () -> Unit) {
    ExpenseScreen(onBackPressed = onBackPressed)
}

@Composable
fun ExpenseScreen(onBackPressed: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var source by remember { mutableStateOf("card") }
    var establishment by remember { mutableStateOf("") }
    var installments by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Column {
        SavesTopBar(
            title = stringResource(id = R.string.register_expense),
            showBackButton = true,
            onBackPressed = onBackPressed
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(stringResource(id = R.string.title)) })

                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = {
                        if (it.length <= 16) {
                            value = it
                        }
                    },
                    visualTransformation = NumberVisualTransformation(),
                    placeholder = { Text(stringResource(id = R.string.value)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                SourceSelection(selected = source, onClick = { source = it })
                if (source == "card") {
                    Dropdown(
                        placeholder = { Text("Installments") },
                        notSetLabel = "Number of installments",
                        items = (1..24).toList(),
                        selectedItem = if (installments.isNotEmpty()) installments.toInt() else null,
                        onItemSelected = { _, item ->
                            installments = item.toString()
                        }
                    )
                }
                SavesTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = establishment,
                    onValueChange = { establishment = it },
                    placeholder = { Text(stringResource(id = R.string.establishment)) })

                DatePickerField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Date") },
                    selectedDate = if (date.isNotBlank()) date.toLong().getDateString() else null
                ) { dateInMillis ->
                    Log.d("TAG", "CashInBottomSheet: $dateInMillis")
                    dateInMillis?.let {
                        date = it.toString()
                    }
                }
            }

            SavesButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }

        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun SourceItem(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: (source: String) -> Unit
) {
    val backgroundColor =
        if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface

    val textColor =
        if (selected) MaterialTheme.colorScheme.background
        else MaterialTheme.colorScheme.onBackground

    SavesButton(
        modifier = modifier,
        onClick = { onClick(label) },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Text(text = label, color = textColor)
    }
}

@Composable
fun SourceSelection(
    modifier: Modifier = Modifier,
    selected: String,
    onClick: (source: String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SourceItem(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            "Conta",
            selected == "account"
        ) { onClick("account") }
        SourceItem(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            "Cartão",
            selected == "card"
        ) { onClick("card") }
    }
}

@Preview
@Composable
fun SourceSelectionPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SourceSelection(modifier = Modifier, selected = "card") {}
        }
    }
}
