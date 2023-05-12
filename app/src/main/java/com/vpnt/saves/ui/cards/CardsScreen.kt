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
import com.vpnt.saves.ui.designsystem.components.SavesCard
import com.vpnt.saves.ui.designsystem.components.SavesText

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
    SavesCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                SavesText(text = creditCard.title)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SavesText(text = "Available limit")
                    SavesText(
                        text = creditCard.availableLimit.toCurrency(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Column {
                    SavesText(text = "Invoice")
                    SavesText(
                        text = creditCard.invoice.toCurrency(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Expiration ${creditCard.expiration.format()}")
        }
    }
}
