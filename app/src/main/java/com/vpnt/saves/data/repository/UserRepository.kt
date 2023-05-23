package com.vpnt.saves.data.repository

import com.parse.ParseUser

interface UserRepository {

    suspend fun user(): ParseUser?

    suspend fun login(username: String, password: String): ParseUser?
}