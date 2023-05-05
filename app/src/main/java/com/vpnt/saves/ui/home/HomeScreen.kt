package com.vpnt.saves.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vpnt.saves.R
import com.vpnt.saves.data.model.Transaction
import com.vpnt.saves.extensions.format
import com.vpnt.saves.extensions.toCurrency
import com.vpnt.saves.ui.designsystem.icon.SavesIcons

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        Header()
        Spacer(modifier = Modifier.size(20.dp))
        BalanceOverview()
        Spacer(modifier = Modifier.size(16.dp))
        Actions()
        Spacer(modifier = Modifier.size(20.dp))
        Transactions()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(SavesIcons.User),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = ShapeDefaults.ExtraLarge)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(text = "Hello")
            Text(text = "Valdir \uD83D\uDC4B")
        }
    }
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
            title = "Monthly Expenses",
            value = 3985.99
        )
        Spacer(modifier = Modifier.size(8.dp))
        BalanceCard(
            title = "Balance available",
            value = 4587.99
        )
    }
}

@Composable
fun BalanceCard(
    title: String,
    value: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = title)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = value.toCurrency(), style = MaterialTheme.typography.headlineMedium)
            }

            Button(
                onClick = { /*TODO*/ },
                shape = CircleShape,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(painter = painterResource(id = SavesIcons.EyeClosed), contentDescription = null)
            }
        }
    }
}

@Composable
fun Actions() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Action(
            text = "Cash in",
            icon = R.drawable.cash_in,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Action(
            text = "Cash out",
            icon = R.drawable.cash_out,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Action(
            text = "Payment",
            icon = R.drawable.qr_code,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
fun Action(
    text: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        shape = ShapeDefaults.Medium,
        onClick = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
        Text(text = "Transactions", style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp))
        Text(text = "See all", style = MaterialTheme.typography.titleSmall)
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
            key = { transaction -> transaction.id}
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
                    modifier = Modifier
                        .clip(shape = ShapeDefaults.ExtraLarge)
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f))
                        .padding(8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = transaction.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = transaction.date.format(), maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall)
                }
                Text(text = transaction.amount.toCurrency(), style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
