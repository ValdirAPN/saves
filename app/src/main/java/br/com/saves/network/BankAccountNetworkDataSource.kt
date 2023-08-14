package br.com.saves.network

import br.com.saves.network.model.NetworkBankAccount
import kotlinx.coroutines.flow.Flow

interface BankAccountNetworkDataSource {

    suspend fun create(bankAccount: NetworkBankAccount)

    suspend fun get(): Flow<List<NetworkBankAccount>>
}