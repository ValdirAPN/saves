package com.vpnt.saves.ui.bankaccounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vpnt.saves.data.model.BankAccount
import com.vpnt.saves.extensions.toCurrency
import com.vpnt.saves.ui.designsystem.components.SavesButton
import com.vpnt.saves.ui.designsystem.components.SavesCard
import com.vpnt.saves.ui.designsystem.components.SavesText
import com.vpnt.saves.ui.designsystem.icon.SavesIcons
import com.vpnt.saves.ui.designsystem.theme.SavesTheme

@Composable
fun BankAccountsRoute(
    modifier: Modifier = Modifier,
    viewModel: BankAccountsViewModel = hiltViewModel()
) {

    val bankAccountsUiState: BankAccountsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    BankAccountsScreen(
        uiState = bankAccountsUiState,
        modifier = modifier
    )
}

@Composable
fun BankAccountsScreen(
    uiState: BankAccountsUiState,
    modifier: Modifier = Modifier
) {
    var showBankAccountBottomSheet by remember {
        mutableStateOf(false)
    }

    fun onClickNewBankAccount() {
        showBankAccountBottomSheet = true
    }

    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        when (uiState) {
            is BankAccountsUiState.Success -> {
                Header(onClickNewCard = { onClickNewBankAccount() })
                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = BankAccount.fakeList(),
                        key = { bankAccount -> bankAccount.id }
                    ) { bankAccount ->
                        BankAccountContainer(bankAccount = bankAccount)
                    }
                }

                if (showBankAccountBottomSheet) {
                    NewBankAccountBottomSheet(onDismissRequest = {
                        showBankAccountBottomSheet = false
                    })
                }
            }

            else -> {}
        }
    }
}

@Preview
@Composable
fun BankAccountsScreenPreview() {
    SavesTheme {
        BankAccountsScreen(
            uiState = BankAccountsUiState.Success(
                accounts = BankAccount.fakeList()
            )
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onClickNewCard: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        SavesText(text = "Contas", style = MaterialTheme.typography.titleLarge)

        Button(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = { onClickNewCard() }
        ) {
            Icon(painter = painterResource(id = SavesIcons.Plus), contentDescription = "")
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Nova conta")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBankAccountBottomSheet(
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = SheetState(skipPartiallyExpanded = true),
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
        ) {
            SavesText(text = "Nova conta", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Título", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Saldo", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))

            SavesButton(
                text = "Adicionar",
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            )
        }
    }
}

@Composable
fun BankAccountContainer(bankAccount: BankAccount) {
    SavesCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                SavesText(text = bankAccount.name, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                SavesText(text = "Balance")
                SavesText(
                    text = bankAccount.balance.toCurrency(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}