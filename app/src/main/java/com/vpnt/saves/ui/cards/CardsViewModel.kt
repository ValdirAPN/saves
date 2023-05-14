package com.vpnt.saves.ui.cards

import androidx.lifecycle.ViewModel
import com.vpnt.saves.data.model.CreditCard
import com.vpnt.saves.data.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CardsUiState.Success(
        cards = CreditCard.fakeList()
    ))

    val uiState = _uiState.asStateFlow()
}

sealed interface CardsUiState {
    data class Success(
        val cards: List<CreditCard>,
    ) : CardsUiState

    object Error : CardsUiState

    object Loading : CardsUiState
}
