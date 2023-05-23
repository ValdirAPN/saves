package com.vpnt.saves.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseUser
import com.vpnt.saves.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserStateViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkUserState()
    }

    private fun checkUserState() {
        viewModelScope.launch {
            userRepository.user().let { user ->
                Log.d(TAG, "updateUserState: $user")
                _authState.value = if (user?.isAuthenticated == true) {
                    AuthState.Authenticated(user)
                } else {
                    AuthState.Unauthenticated
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = userRepository.login(username, password)

            _authState.value = if (result != null) {
                AuthState.Authenticated(result)
            } else {
                AuthState.Unauthenticated
            }
        }
    }
}

sealed interface AuthState {
    object Loading : AuthState
    data class Authenticated(val user: ParseUser?) : AuthState
    object Unauthenticated : AuthState
}
