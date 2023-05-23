package com.vpnt.saves.network

import com.parse.ParseUser
import kotlinx.coroutines.flow.Flow

interface UserNetworkDataSource {

    suspend fun user(): ParseUser?

    suspend fun login(username: String, password: String): ParseUser?
}