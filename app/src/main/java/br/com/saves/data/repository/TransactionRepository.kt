package br.com.saves.data.repository

import br.com.saves.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getAll(): Flow<List<Transaction>>
    suspend fun create(transaction: Transaction)
    suspend fun update(transaction: Transaction)
}