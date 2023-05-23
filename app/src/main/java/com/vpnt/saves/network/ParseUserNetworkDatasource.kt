package com.vpnt.saves.network

import com.parse.ParseUser

class ParseUserNetworkDatasource : UserNetworkDataSource {

    override suspend fun user(): ParseUser? = ParseUser.getCurrentUser()

    override suspend fun login(username: String, password: String): ParseUser? {
        return try {
            ParseUser.logIn(username, password)
        } catch (e: Exception) {
            null
        }
    }
}