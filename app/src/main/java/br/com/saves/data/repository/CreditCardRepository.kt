package br.com.saves.data.repository

import br.com.saves.model.CreditCard
import kotlinx.coroutines.flow.Flow

interface CreditCardRepository {

    fun getAll(): Flow<List<CreditCard>>
    suspend fun create(creditCard: CreditCard)
}