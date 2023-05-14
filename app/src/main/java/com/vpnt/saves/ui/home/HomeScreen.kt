package com.vpnt.saves.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vpnt.saves.R
import com.vpnt.saves.data.model.Transaction
import com.vpnt.saves.extensions.format
import com.vpnt.saves.extensions.toCurrency
import com.vpnt.saves.ui.designsystem.components.SavesButton
import com.vpnt.saves.ui.designsystem.components.SavesCard
import com.vpnt.saves.ui.designsystem.components.SavesClickableCard
import com.vpnt.saves.ui.designsystem.components.SavesText
import com.vpnt.saves.ui.designsystem.icon.SavesIcons
import com.vpnt.saves.ui.designsystem.theme.SavesTheme

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val homeUiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = homeUiState,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    var showCashInBottomSheet by remember { mutableStateOf(false) }
    var showCashOutBottomSheet by remember { mutableStateOf(false) }

    fun onClickCashIn() {
        showCashInBottomSheet = true
    }

    fun onClickCashOut() {
        showCashOutBottomSheet = true
    }

    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        when (uiState) {
            is HomeUiState.Success -> {
                Spacer(modifier = Modifier.size(16.dp))
                BalanceOverview(
                    balance = uiState.balance,
                    monthExpenses = uiState.monthExpenses
                )
                Spacer(modifier = Modifier.size(24.dp))
                Actions(
                    onClickCashIn = { onClickCashIn() },
                    onClickCashOut = { onClickCashOut() }
                )
                Spacer(modifier = Modifier.size(24.dp))
                Transactions(uiState.transactions)
                Spacer(modifier = Modifier.size(16.dp))

                if (showCashInBottomSheet) {
                    CashInBottomSheet(onDismissRequest = { showCashInBottomSheet = false })
                }

                if (showCashOutBottomSheet) {
                    CashOutBottomSheet(onDismissRequest = { showCashOutBottomSheet = false })

                }
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashInBottomSheet(
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
            SavesText(text = "Registrar saída", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Nome", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Limite total", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Limite utilizado", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))

            SavesButton(
                text = "Registrar",
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashOutBottomSheet(
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
            SavesText(text = "Registrar saída", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Título", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Quantidade", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))
            SavesText(text = "Data", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(16.dp))

            SavesButton(
                text = "Registrar",
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SavesTheme {
        HomeScreen(
            uiState = HomeUiState.Success(
                balance = 100.0,
                monthExpenses = 2016.99,
                transactions = Transaction.fakeList()
            )
        )
    }
}

@Composable
fun BalanceOverview(
    balance: Double,
    monthExpenses: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BalanceCard(
            title = "Saldo",
            icon = SavesIcons.Coins,
            value = balance
        )
        Spacer(modifier = Modifier.size(8.dp))
        BalanceCard(
            title = "Despesas do mês",
            icon = SavesIcons.ReceiptX,
            value = monthExpenses
        )
    }
}

@Composable
fun BalanceCard(
    title: String,
    @DrawableRes icon: Int,
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(painter = painterResource(id = icon), contentDescription = "")
                Text(text = title)
            }
            Spacer(modifier = Modifier.size(8.dp))
            SavesText(
                text = value.toCurrency(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 40.sp,
                    lineHeight = 40.sp
                )
            )
        }
    }
}

@Composable
fun Actions(
    onClickCashIn: () -> Unit,
    onClickCashOut: () -> Unit,
) {
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
                onClick = onClickCashIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Action(
                text = "Registrar \nSaída",
                icon = R.drawable.handcoins,
                onClick = onClickCashOut,
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SavesClickableCard(
        modifier = modifier,
        onClick = { onClick() }
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
fun Transactions(transactions: List<Transaction>) {
    Column {
        TransactionsHeader()
        Spacer(modifier = Modifier.size(16.dp))
        TransactionsList(transactions)
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
        SavesText(
            text = "See all",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun TransactionsList(transactions: List<Transaction>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        for (transaction in transactions) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(SavesIcons.User),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clip(shape = ShapeDefaults.Medium)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    SavesText(
                        text = transaction.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    SavesText(
                        text = transaction.date.format(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                SavesText(
                    text = transaction.amount.toCurrency(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
