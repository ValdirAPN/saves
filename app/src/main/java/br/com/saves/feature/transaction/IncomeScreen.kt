package br.com.saves.feature.transaction

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.saves.R
import br.com.saves.model.Transaction
import br.com.saves.model.TransactionType
import br.com.saves.ui.composables.DatePickerField
import br.com.saves.ui.composables.Dropdown
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.composables.SavesTextField
import br.com.saves.ui.composables.SavesTopBar
import br.com.saves.utils.MONETARY_NUMBER_MAX_LENGTH
import br.com.saves.utils.NumberVisualTransformation
import br.com.saves.utils.getDateString
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone
import java.util.UUID

@Composable
fun IncomeRoute(
    onBackPressed: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val uiState: TransactionUiState by viewModel.uiState.collectAsStateWithLifecycle()
    IncomeScreen(
        onBackPressed = onBackPressed,
        onCreateTransaction = viewModel::createTransaction,
        uiState = uiState
    )
}

@Composable
fun IncomeScreen(
    onBackPressed: () -> Unit,
    onCreateTransaction: (Transaction) -> Unit,
    uiState: TransactionUiState
) {
    when (uiState) {
        is TransactionUiState.Loading -> {}
        is TransactionUiState.Success -> {
            var description by remember { mutableStateOf("") }
            var amount by remember { mutableStateOf("") }
            var accountId by remember { mutableStateOf("") }
            var date by remember { mutableStateOf("") }

            var showSuccessDialog by remember { mutableStateOf(false) }

            if (showSuccessDialog) {
                CreateTransactionSuccessDialog {
                    showSuccessDialog = false
                    onBackPressed()
                }
            }

            Column {
                SavesTopBar(
                    title = stringResource(id = R.string.register_income),
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
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text(stringResource(id = R.string.description)) }
                        )

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

                        Dropdown(
                            placeholder = { Text(stringResource(id = R.string.account)) },
                            notSetLabel = stringResource(id = R.string.account),
                            items = uiState.bankAccounts,
                            itemToString = { it.name },
                            selectedItem = if (accountId.isNotEmpty()) uiState.bankAccounts.find { it.id == accountId } else null,
                            onItemSelected = { _, item ->
                                accountId = item.id
                            }
                        )

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
                                description = description,
                                date = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.toLong()), TimeZone.getDefault().toZoneId()),
                                amount = amount.toDouble() / 100,
                                installments = 1,
                                type = TransactionType.INCOME,
                                bankAccountId = accountId
                            )
                            onCreateTransaction(transaction)
                            showSuccessDialog = true
                        },
                        enabled = isIncomeFormValid(description, date, amount, accountId)
                    ) {
                        Text(text = stringResource(id = R.string.register))
                    }
                }

                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }
}

private fun isIncomeFormValid(
    description: String,
    date: String,
    amount: String,
    accountId: String
): Boolean {
    return description.trim().isNotBlank()
            && date.trim().isNotBlank()
            && amount.trim().isNotBlank()
            && accountId.trim().isNotBlank()
}
