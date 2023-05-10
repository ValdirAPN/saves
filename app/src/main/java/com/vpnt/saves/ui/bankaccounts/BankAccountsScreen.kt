package com.vpnt.saves.ui.bankaccounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vpnt.saves.data.model.BankAccount
import com.vpnt.saves.extensions.toCurrency

@Composable
fun BankAccountsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        items(
            items = BankAccount.fakeList(),
            key = { bankAccount -> bankAccount.id }
        ) { bankAccount ->
            Spacer(modifier = Modifier.size(16.dp))
            BankAccountContainer(bankAccount = bankAccount)
        }
    }
}

@Preview
@Composable
fun BankAccountsScreenPreview() {
    BankAccountsScreen()
}

@Composable
fun BankAccountContainer(bankAccount: BankAccount) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                Text(text = bankAccount.name)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = "Balance")
                Text(text = bankAccount.balance.toCurrency())
            }
        }
    }
}