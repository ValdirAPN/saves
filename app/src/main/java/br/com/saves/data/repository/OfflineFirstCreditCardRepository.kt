package br.com.saves.data.repository

import br.com.saves.database.dao.CreditCardDao
import br.com.saves.database.model.CreditCardEntity
import br.com.saves.database.model.asModel
import br.com.saves.model.CreditCard
import br.com.saves.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCreditCardRepository @Inject constructor(
    private val dao: CreditCardDao
) : CreditCardRepository {
    override fun getAll(): Flow<List<CreditCard>> =
        dao.getEntities()
            .map { it.map(CreditCardEntity::asModel) }

    override suspend fun create(creditCard: CreditCard) {
        dao.insertOrIgnore(creditCard.asEntity())
    }
}