package com.vpnt.saves.data.repository

import com.parse.ParseUser
import com.vpnt.saves.network.Dispatcher
import com.vpnt.saves.network.SavesDispatchers
import com.vpnt.saves.network.UserNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @Dispatcher(SavesDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepository {

    override suspend fun user() = userNetworkDataSource.user()

    override suspend fun login(username: String, password: String) = userNetworkDataSource.login(username, password)

}