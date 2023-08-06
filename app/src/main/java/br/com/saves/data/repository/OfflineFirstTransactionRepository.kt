package br.com.saves.data.repository

import br.com.saves.database.dao.TransactionDao
import br.com.saves.database.model.TransactionEntity
import br.com.saves.database.model.asModel
import br.com.saves.model.Transaction
import br.com.saves.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstTransactionRepository @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {
    override fun getAll(): Flow<List<Transaction>> =
        dao.getEntities()
            .map { it.map(TransactionEntity::asModel) }

    override suspend fun create(transaction: Transaction) {
        dao.insertOrIgnore(transaction.asEntity())
    }

    override suspend fun update(transaction: Transaction) {
        dao.upsert(transaction.asEntity())
    }
}