package br.com.saves.feature.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.saves.domain.CreateTransactionUseCase
import br.com.saves.domain.GetBankAccountsUseCase
import br.com.saves.domain.GetCreditCardsUseCase
import br.com.saves.model.BankAccount
import br.com.saves.model.CreditCard
import br.com.saves.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    getBankAccountsUseCase: GetBankAccountsUseCase,
    getCreditCardsUseCase: GetCreditCardsUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
) : ViewModel() {

    val uiState: StateFlow<TransactionUiState> = combine(
        getBankAccountsUseCase(),
        getCreditCardsUseCase()
    ) { bankAccounts, creditCards ->
        TransactionUiState.Success(
            bankAccounts = bankAccounts,
            creditCards = creditCards
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = TransactionUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun createTransaction(transaction: Transaction) {
        viewModelScope.launch {
            createTransactionUseCase(transaction)
        }
    }
}

sealed interface TransactionUiState {
    object Loading: TransactionUiState
    data class Success(
        val bankAccounts: List<BankAccount>,
        val creditCards: List<CreditCard>
    ): TransactionUiState
}
