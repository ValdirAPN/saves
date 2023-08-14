package br.com.saves.data.repository

import br.com.saves.database.dao.BankAccountDao
import br.com.saves.database.model.BankAccountEntity
import br.com.saves.database.model.asModel
import br.com.saves.model.BankAccount
import br.com.saves.model.asEntity
import br.com.saves.network.BankAccountNetworkDataSource
import br.com.saves.network.model.asEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstBankAccountRepository @Inject constructor(
    private val dao: BankAccountDao,
    private val network: BankAccountNetworkDataSource
) : BankAccountRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAll(): Flow<List<BankAccount>> =
        dao.getEntities()
            .flatMapLatest { entities ->
                if (entities.isEmpty()) {
                    refresh()
                }
                dao.getEntities()
            }
            .map { it.map(BankAccountEntity::asModel) }

    override suspend fun create(bankAccount: BankAccount) {
        dao.insertOrIgnore(bankAccount.asEntity())
    }

    override suspend fun update(bankAccount: BankAccount) {
        dao.upsert(bankAccount.asEntity())
    }

    override suspend fun refresh() {
        network.get()
            .collect {
                it
                    .map { networkBankAccount -> networkBankAccount.asEntity() }
                    .also { entities -> dao.upsert(entities) }
            }
    }
}