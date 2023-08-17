package br.com.saves.feature.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.saves.R
import br.com.saves.feature.home.TransactionContainer
import br.com.saves.model.Transaction
import br.com.saves.model.TransactionType
import br.com.saves.ui.composables.SavesTopBar
import br.com.saves.utils.toCurrency
import java.time.LocalDateTime

@Composable
fun TransactionsRoute(
    onBackPressed: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val uiState: TransactionUiState by viewModel.uiState.collectAsStateWithLifecycle()

    TransactionsScreen(
        onBackPressed = onBackPressed,
        uiState = uiState
    )
}

@Composable
fun TransactionsScreen(
    onBackPressed: () -> Unit,
    uiState: TransactionUiState,
) {
    when (uiState) {
        is TransactionUiState.Loading -> {}
        is TransactionUiState.Success -> {
            val months = listOf(
                "Janeiro",
                "Fevereiro",
                "Março",
                "Abril",
                "Maio",
                "Junho",
                "Julho",
                "Agosto",
                "Setembro",
                "Outubro",
                "Novembro",
                "Dezembro"
            )

            val monthsListState = rememberLazyListState()

            var selectedMonth by remember { mutableStateOf(LocalDateTime.now().monthValue - 1) }
            var selectedMonthTransactions by remember { mutableStateOf(listOf<Transaction>()) }

            var monthIncomes by remember { mutableStateOf(0.0) }
            var monthExpenses by remember { mutableStateOf(0.0) }
            var monthBalance by remember { mutableStateOf(0.0) }

            LaunchedEffect(selectedMonth) {
                monthsListState.scrollToItem(selectedMonth)
                selectedMonthTransactions =
                    if (uiState.transactions.isEmpty()) {
                        emptyList()
                    } else {
                        uiState.transactions.filter { it.date.month.value == (selectedMonth + 1) }
                    }
            }

            LaunchedEffect(selectedMonthTransactions) {
                monthIncomes = 0.0
                monthExpenses = 0.0
                monthBalance = 0.0

                if (selectedMonthTransactions.isNotEmpty()) {
                    selectedMonthTransactions.forEach { transaction ->
                        when (transaction.type) {
                            TransactionType.INCOME -> {
                                monthIncomes += transaction.amount
                                monthBalance += transaction.amount
                            }
                            TransactionType.EXPENSE -> {
                                monthExpenses += transaction.amount
                                monthBalance -= transaction.amount
                            }
                        }
                    }
                }
            }

            Column {
                SavesTopBar(
                    title = stringResource(id = R.string.transactions),
                    showBackButton = true,
                    onBackPressed = onBackPressed
                )
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(id = R.string.month_balance),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(monthBalance.toCurrency(), style = MaterialTheme.typography.headlineLarge)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            MonthBalance(type = TransactionType.INCOME, amount = monthIncomes)
                            Spacer(modifier = Modifier.size(8.dp))
                            MonthBalance(type = TransactionType.EXPENSE, amount = monthExpenses)
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        LazyRow(
                            state = monthsListState,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                Spacer(modifier = Modifier.size(16.dp))
                            }
                            itemsIndexed(
                                items = months,
                                itemContent = { index, item ->
                                    val containerColor =
                                        if (selectedMonth == index) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surface

                                    val contentColor =
                                        if (selectedMonth == index) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.onSurface

                                    Text(
                                        text = item,
                                        color = contentColor,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(100f))
                                            .background(containerColor)
                                            .clickable {
                                                selectedMonth = index
                                            }
                                            .padding(8.dp, 4.dp)
                                    )
                                }
                            )
                            item {
                                Spacer(modifier = Modifier.size(16.dp))
                            }
                        }

                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .fillMaxSize()
                            ) {
                                if (selectedMonthTransactions.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.not_transactions_found),
                                        modifier = Modifier
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                } else {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        contentPadding = PaddingValues(16.dp)
                                    ) {
                                        items(
                                            items = selectedMonthTransactions,
                                            key = { transaction -> transaction.id }
                                        ) { transaction ->
                                            TransactionContainer(transaction = transaction)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MonthBalance(
    modifier: Modifier = Modifier,
    type: TransactionType,
    amount: Double
) {
    val background = when (type) {
        TransactionType.INCOME -> MaterialTheme.colorScheme.primary
        TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
    }

    val icon = when (type) {
        TransactionType.INCOME -> painterResource(id = R.drawable.arrowup)
        TransactionType.EXPENSE -> painterResource(id = R.drawable.arrowdown)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(background)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(painter = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
        Text(text = amount.toCurrency(), color = MaterialTheme.colorScheme.onPrimary)
    }
}
