package br.com.saves.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.saves.R
import br.com.saves.model.Account
import br.com.saves.model.CreditCard
import br.com.saves.model.Transaction

@Composable
fun HomeRoute(
    navigateToIncome: () -> Unit,
    navigateToExpense: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        navigateToIncome = navigateToIncome,
        navigateToExpense = navigateToExpense,
        uiState = uiState
    )
}

@Composable
fun HomeScreen(
    navigateToIncome: () -> Unit,
    navigateToExpense: () -> Unit,
    uiState: HomeUiState
) {
    when (uiState) {
        HomeUiState.Loading -> {
            Text(text = "Loading")
        }

        is HomeUiState.Success -> {
            var showAccountBottomSheet by remember { mutableStateOf(false) }
            var showCardBottomSheet by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.size(24.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    HomeHeader(username = uiState.userData.name)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BalanceOverview(modifier = Modifier.fillMaxWidth())
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Action(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                onClick = {
                                    navigateToIncome()
                                },
                                icon = R.drawable.currency_circle_dollar,
                                label = stringResource(id = R.string.register_income)
                            )
                            Action(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                onClick = {
                                    navigateToExpense()
                                },
                                icon = R.drawable.receipt,
                                label = stringResource(id = R.string.register_expense),
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        }
                        TransactionsContainer(transactions = Transaction.fakeList())
                    }
                    AccountsContainer(
                        accounts = Account.fakeList(),
                        onClickAddNewAccount = { showAccountBottomSheet = true }
                    )
                    CardsContainer(
                        creditCards = CreditCard.fakeList(),
                        onClickAddNewCard = { showCardBottomSheet = true }
                    )
                    Spacer(modifier = Modifier.size(8.dp))

                    if (showAccountBottomSheet) {
                        NewAccountForm(
                            onDismissRequest = { showAccountBottomSheet = false },
                            onCreateAccount = {}
                        )
                    }

                    if (showCardBottomSheet) {
                        NewCardForm(
                            onDismissRequest = { showCardBottomSheet = false },
                            onCreateCard = {}
                        )
                    }
                }
            }
        }
    }
}
