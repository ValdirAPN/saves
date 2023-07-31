package br.com.saves.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.R
import br.com.saves.model.Transaction
import br.com.saves.model.TransactionType
import br.com.saves.ui.theme.SavesTheme
import br.com.saves.utils.formatDate
import br.com.saves.utils.toCurrency
import java.util.Date


@Composable
fun TransactionsContainer(modifier: Modifier = Modifier, transactions: List<Transaction>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transações",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Text(text = "Ver mais", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            transactions.forEach { transaction ->
                TransactionContainer(transaction = transaction)
            }
        }
    }
}

@Preview
@Composable
fun TransactionsContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            TransactionsContainer(
                modifier = Modifier,
                transactions = Transaction.fakeList()
            )
        }
    }
}

@Composable
fun TransactionContainer(modifier: Modifier = Modifier, transaction: Transaction) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val valueColor = when (transaction.type) {
            TransactionType.INCOME -> MaterialTheme.colorScheme.primary
            TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
        }
        Icon(
            painter = painterResource(id = R.drawable.coins),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = transaction.title)
            Text(
                text = transaction.date.formatDate(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(text = transaction.value.toCurrency(), color = valueColor)
    }
}

@Preview
@Composable
fun TransactionContainerPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            TransactionContainer(
                transaction = Transaction(
                    title = "Cinema",
                    date = Date(),
                    value = 56.70,
                    type = TransactionType.INCOME
                )
            )
        }
    }
}