package br.com.saves.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.saves.data.repository.UserRepository
import br.com.saves.domain.CreateBankAccountUseCase
import br.com.saves.domain.CreateCreditCardUseCase
import br.com.saves.domain.CreateTransactionUseCase
import br.com.saves.domain.GetBankAccountsUseCase
import br.com.saves.domain.GetCreditCardsUseCase
import br.com.saves.domain.GetTransactionsUseCase
import br.com.saves.domain.UpdateBankAccountUseCase
import br.com.saves.model.BankAccount
import br.com.saves.model.CreditCard
import br.com.saves.model.Transaction
import br.com.saves.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    getCreditCardsUseCase: GetCreditCardsUseCase,
    getBankAccountsUseCase: GetBankAccountsUseCase,
    getTransactionsUseCase: GetTransactionsUseCase,
    private val createCreditCardUseCase: CreateCreditCardUseCase,
    private val createBankAccountUseCase: CreateBankAccountUseCase
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = combine(
        userRepository.getUserData(),
        getCreditCardsUseCase(),
        getBankAccountsUseCase(),
        getTransactionsUseCase(),
    ) { userData, creditCards, bankAccounts, transactions ->
        HomeUiState.Success(
            userData = userData,
            creditCards = creditCards,
            bankAccounts = bankAccounts,
            transactions = transactions.asReversed().subList(0, 4),
            balance = if (bankAccounts.isEmpty()) 0.0 else bankAccounts.map { it.balance }.reduce { acc, balance -> acc + balance }
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun createCreditCard(creditCard: CreditCard) {
        viewModelScope.launch {
            createCreditCardUseCase(creditCard)
        }
    }

    fun createBankAccount(bankAccount: BankAccount) {
        viewModelScope.launch {
            createBankAccountUseCase(bankAccount)
        }
    }
}

sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Success(
        val userData: UserData,
        val creditCards: List<CreditCard>,
        val bankAccounts: List<BankAccount>,
        val transactions: List<Transaction>,
        val balance: Double
    ): HomeUiState
}
