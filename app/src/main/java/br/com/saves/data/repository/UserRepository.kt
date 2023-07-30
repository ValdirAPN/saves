package br.com.saves.data.repository

import br.com.saves.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun isAuthenticated(): Flow<Boolean>
    fun getUserData(): Flow<UserData>
}