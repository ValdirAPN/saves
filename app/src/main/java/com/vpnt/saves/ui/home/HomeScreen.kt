package com.vpnt.saves.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vpnt.saves.R
import com.vpnt.saves.data.model.Transaction
import com.vpnt.saves.extensions.format
import com.vpnt.saves.extensions.toCurrency
import com.vpnt.saves.ui.designsystem.components.SavesCard
import com.vpnt.saves.ui.designsystem.components.SavesClickableCard
import com.vpnt.saves.ui.designsystem.components.SavesText
import com.vpnt.saves.ui.designsystem.icon.SavesIcons

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        BalanceOverview()
        Spacer(modifier = Modifier.size(24.dp))
        Actions()
        Spacer(modifier = Modifier.size(24.dp))
        Transactions()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun BalanceOverview(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BalanceCard(
            title = "Saldo",
            value = 0.00
        )
        Spacer(modifier = Modifier.size(8.dp))
        BalanceCard(
            title = "Despesas do mês",
            value = 2016.99
        )
    }
}

@Composable
fun BalanceCard(
    title: String,
    value: Double
) {
    SavesCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.size(8.dp))
            SavesText(text = value.toCurrency(), style = MaterialTheme.typography.bodyLarge.copy(fontSize = 40.sp, lineHeight = 40.sp))
        }
    }
}

@Composable
fun Actions() {
    Column {
        Text(text = "Ações rápidas", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Action(
                text = "Registrar \nEntrada",
                icon = R.drawable.piggybank,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Action(
                text = "Registrar \nSaída",
                icon = R.drawable.handcoins,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun Action(
    text: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier
) {
    SavesClickableCard(
        modifier = modifier,
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            SavesText(text = text, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun Transactions() {
    Column {
        TransactionsHeader()
        Spacer(modifier = Modifier.size(16.dp))
        TransactionsList()
    }
}

@Composable
fun TransactionsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        SavesText(text = "Transactions", style = MaterialTheme.typography.titleLarge)
        SavesText(text = "See all", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun TransactionsList() {
    val transactions = Transaction.fakeList()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = transactions,
            key = { transaction -> transaction.id }
        ) { transaction ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(SavesIcons.User),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .clip(shape = ShapeDefaults.ExtraLarge)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    SavesText(text = transaction.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.size(4.dp))
                    SavesText(text = transaction.date.format(), maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall)
                }
                SavesText(text = transaction.amount.toCurrency(), style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
