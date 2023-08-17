package br.com.saves.feature.bankaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.saves.domain.GetBankAccountsUseCase
import br.com.saves.model.BankAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BankAccountsViewModel @Inject constructor(
    getBankAccountsUseCase: GetBankAccountsUseCase
) : ViewModel() {

    val uiState: StateFlow<BankAccountsUiState> = combine(
        getBankAccountsUseCase()
    ) {
        BankAccountsUiState.Success(
            bankAccounts = it.first()
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = BankAccountsUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface BankAccountsUiState {
    object Loading: BankAccountsUiState
    data class Success(
        val bankAccounts: List<BankAccount>,
    ): BankAccountsUiState
}
