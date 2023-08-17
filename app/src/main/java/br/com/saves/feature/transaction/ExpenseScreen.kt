package br.com.saves.feature.transaction

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.saves.R
import br.com.saves.model.BankAccount
import br.com.saves.model.Category
import br.com.saves.model.CreditCard
import br.com.saves.model.Transaction
import br.com.saves.model.TransactionType
import br.com.saves.ui.composables.DatePickerField
import br.com.saves.ui.composables.FinanceInstitutionDropdown
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.composables.SavesTopBar
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.MONETARY_NUMBER_MAX_LENGTH
import br.com.saves.utils.NumberVisualTransformation
import br.com.saves.utils.getDateString
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone
import java.util.UUID

@Composable
fun ExpenseRoute(
    onBackPressed: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val uiState: TransactionUiState by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseScreen(
        onBackPressed = onBackPressed,
        onCreateTransaction = viewModel::createTransaction,
        uiState = uiState
    )
}

@Composable
fun ExpenseScreen(
    onBackPressed: () -> Unit,
    onCreateTransaction: (Transaction) -> Unit,
    uiState: TransactionUiState
) {
    when (uiState) {
        is TransactionUiState.Loading -> {}
        is TransactionUiState.Success -> {
            var description by remember { mutableStateOf("") }
            var category by remember { mutableStateOf("") }
            var amount by remember { mutableStateOf("") }
            var source by remember { mutableStateOf("account") }
            var installments by remember { mutableStateOf("") }
            var date by remember { mutableStateOf("") }
            var accountId by remember { mutableStateOf("") }
            var creditCardId by remember { mutableStateOf("") }

            var showSuccessDialog by remember { mutableStateOf(false) }
            var showErrorDialog by remember { mutableStateOf(false) }

            if (showSuccessDialog) {
                CreateTransactionSuccessDialog {
                    showSuccessDialog = false
                    onBackPressed()
                }
            }

            if (showErrorDialog) {
                ErrorDialog {
                    showErrorDialog = false
                }
            }

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
                        val categories = Category.values().asList()
                        CategoryDropdown(
                            placeholder = { Text(stringResource(id = R.string.category)) },
                            items = categories,
                            selectedItem = if (category.isNotEmpty()) Category.valueOf(category) else null,
                            onItemSelected = { _, item ->
                                category = item.name
                            }
                        )

                        SavesTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text(stringResource(id = R.string.description)) })

                        SavesTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = amount,
                            onValueChange = {
                                if (it.length <= MONETARY_NUMBER_MAX_LENGTH) {
                                    amount = it
                                }
                            },
                            visualTransformation = NumberVisualTransformation(),
                            placeholder = { Text(stringResource(id = R.string.value)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                        )
                        SourceSelection(
                            selected = source,
                            onClick = {
                                if (it == "card" && uiState.creditCards.isEmpty()) {
                                    showErrorDialog = true
                                    return@SourceSelection
                                }
                                source = it
                                installments = ""
                                accountId = ""
                                creditCardId = ""
                            }
                        )

                        if (source == "card") {
                            val creditCard = if (creditCardId.isNotEmpty()) uiState.creditCards.find { it.id == creditCardId } else null
                            FinanceInstitutionDropdown(
                                placeholder = { Text(stringResource(id = R.string.card)) },
                                items = uiState.creditCards,
                                selectedItem = creditCard,
                                onItemSelected = { _, item ->
                                    creditCardId = item.id
                                }
                            )
                            SavesTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = installments,
                                onValueChange = {
                                    installments = it
                                },
                                visualTransformation = NumberVisualTransformation(),
                                placeholder = { Text(stringResource(id = R.string.installments)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                            )
                        } else {
                            val bankAccount = if (accountId.isNotEmpty()) uiState.bankAccounts.find { it.id == accountId } else null
                            FinanceInstitutionDropdown(
                                placeholder = { Text(stringResource(id = R.string.account)) },
                                items = uiState.bankAccounts,
                                selectedItem = bankAccount,
                                onItemSelected = { _, item ->
                                    accountId = item.id
                                }
                            )
                        }

                        DatePickerField(
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Date") },
                            selectedDate = if (date.isNotBlank()) date.toLong()
                                .getDateString() else null
                        ) { dateInMillis ->
                            Log.d("TAG", "CashInBottomSheet: $dateInMillis")
                            dateInMillis?.let {
                                date = it.toString()
                            }
                        }
                    }

                    SavesButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val transaction = Transaction(
                                id = UUID.randomUUID().toString(),
                                category = Category.valueOf(category),
                                description = description,
                                date = LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(date.toLong()),
                                    TimeZone.getDefault().toZoneId()
                                ),
                                amount = amount.toDouble() / 100,
                                installments = if (source == "card") installments.toInt() else 1,
                                type = TransactionType.EXPENSE,
                                bankAccountId = accountId.ifEmpty { null },
                                creditCardId = creditCardId.ifEmpty { null }
                            )
                            onCreateTransaction(transaction)
                            showSuccessDialog = true
                        },
                        enabled = isExpenseFormValid(
                            description,
                            amount,
                            source,
                            installments,
                            date,
                            accountId,
                            creditCardId
                        )
                    ) {
                        Text(text = stringResource(id = R.string.register))
                    }
                }

                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Preview
@Composable
fun ExpenseScreenPreview() {
    SavesTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ExpenseScreen(
                onBackPressed = { /*TODO*/ },
                onCreateTransaction = {},
                uiState = TransactionUiState.Success(
                    BankAccount.fakeList(),
                    CreditCard.fakeList(),
                    Transaction.fakeList()
                )
            )
        }
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

private fun isExpenseFormValid(
    description: String,
    amount: String,
    source: String,
    installments: String,
    date: String,
    accountId: String,
    creditCardId: String
): Boolean {
    return when (source) {
        "card" -> {
            description.trim().isNotBlank()
                    && amount.trim().isNotBlank()
                    && (installments.trim().isNotBlank())
                    && date.trim().isNotBlank()
                    && creditCardId.trim().isNotBlank()
        }

        else -> {
            description.trim().isNotBlank()
                    && amount.trim().isNotBlank()
                    && date.trim().isNotBlank()
                    && accountId.trim().isNotBlank()
        }
    }
}

@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable (() -> Unit),
    items: List<Category>,
    selectedItem: Category? = null,
    onItemSelected: (index: Int, item: Category) -> Unit,
    drawItem: @Composable (Category, Boolean, () -> Unit) -> Unit = { item, itemEnabled, onClick ->
        CategoryDropdownItem(
            text = stringResource(id = item.title),
            icon = painterResource(id = item.icon),
            color = item.color,
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
            value = selectedItem?.let { stringResource(id = it.title) } ?: "",
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
fun CategoryDropdownItem(
    text: String,
    icon: Painter,
    color: Color,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(painter = icon, contentDescription = null, tint = color)
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
