package br.com.saves.data.repository

import br.com.saves.database.dao.BankAccountDao
import br.com.saves.database.dao.CreditCardDao
import br.com.saves.database.model.BankAccountEntity
import br.com.saves.database.model.CreditCardEntity
import br.com.saves.database.model.asModel
import br.com.saves.model.BankAccount
import br.com.saves.model.CreditCard
import br.com.saves.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstBankAccountRepository @Inject constructor(
    private val dao: BankAccountDao
) : BankAccountRepository {
    override fun getAll(): Flow<List<BankAccount>> =
        dao.getEntities()
            .map { it.map(BankAccountEntity::asModel) }

    override suspend fun create(bankAccount: BankAccount) {
        dao.insertOrIgnore(bankAccount.asEntity())
    }

    override suspend fun update(bankAccount: BankAccount) {
        dao.upsert(bankAccount.asEntity())
    }
}