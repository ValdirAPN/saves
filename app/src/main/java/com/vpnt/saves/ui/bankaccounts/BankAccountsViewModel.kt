package com.vpnt.saves.ui.bankaccounts

import androidx.lifecycle.ViewModel
import com.vpnt.saves.data.model.BankAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BankAccountsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BankAccountsUiState.Success(
        accounts = BankAccount.fakeList()
    ))

    val uiState = _uiState.asStateFlow()
}

sealed interface BankAccountsUiState {
    data class Success(
        val accounts: List<BankAccount>,
    ) : BankAccountsUiState

    object Error : BankAccountsUiState

    object Loading : BankAccountsUiState
}
