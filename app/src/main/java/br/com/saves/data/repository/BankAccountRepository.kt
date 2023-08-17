package br.com.saves.data.repository

import br.com.saves.model.BankAccount
import kotlinx.coroutines.flow.Flow

interface BankAccountRepository {

    fun getAll(): Flow<List<BankAccount>>
    suspend fun create(bankAccount: BankAccount)

    suspend fun update(bankAccount: BankAccount)

    suspend fun refresh()
}