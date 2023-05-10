package com.vpnt.saves.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vpnt.saves.data.model.CreditCard
import com.vpnt.saves.extensions.format
import com.vpnt.saves.extensions.toCurrency

@Composable
fun CardsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        items(
            items = CreditCard.fakeList(),
            key = { creditCard -> creditCard.id }
        ) { creditCard ->
            Spacer(modifier = Modifier.size(16.dp))
            CardContainer(creditCard = creditCard)
        }
    }
}

@Composable
fun CardContainer(creditCard: CreditCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                Text(text = creditCard.title)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(text = "Available limit")
                    Text(
                        text = creditCard.availableLimit.toCurrency(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column {
                    Text(text = "Invoice")
                    Text(
                        text = creditCard.invoice.toCurrency(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Expiration ${creditCard.expiration.format()}")
        }
    }
}
