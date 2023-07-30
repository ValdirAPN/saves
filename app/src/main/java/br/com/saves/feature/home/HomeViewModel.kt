package br.com.saves.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.saves.data.repository.UserRepository
import br.com.saves.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = combine(
        userRepository.getUserData()
    ) { userData ->
        HomeUiState.Success(userData = userData.first())
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Success(
        val userData: UserData
    ): HomeUiState
}
