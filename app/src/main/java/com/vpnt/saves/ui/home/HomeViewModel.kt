package com.vpnt.saves.ui.home

import androidx.lifecycle.ViewModel
import com.vpnt.saves.data.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState.Success(
        balance = 100.0,
        monthExpenses = 2016.99,
        transactions = Transaction.fakeList().take(4)
    ))
    val uiState = _uiState.asStateFlow()
}

sealed interface HomeUiState {
    data class Success(
        val balance: Double,
        val monthExpenses: Double,
        val transactions: List<Transaction>
    ) : HomeUiState

    object Error : HomeUiState

    object Loading : HomeUiState
}