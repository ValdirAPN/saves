package br.com.saves.data.repository

import br.com.saves.common.FirebaseAuthManager
import br.com.saves.model.UserData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun isAuthenticated(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(FirebaseAuthManager.auth.currentUser != null).isSuccess
        }

        FirebaseAuthManager.auth.addAuthStateListener(authStateListener)

        awaitClose {
            FirebaseAuthManager.auth.removeAuthStateListener(authStateListener)
        }
    }.onStart {
        emit(FirebaseAuthManager.auth.currentUser != null)
    }

    override fun getUserData(): Flow<UserData> = flow {
        val currentUser = FirebaseAuthManager.auth.currentUser

        val userData = UserData(
            name = currentUser?.displayName ?: ""
        )

        emit(userData)
    }
}