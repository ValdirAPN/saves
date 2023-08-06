package br.com.saves.model

import br.com.saves.database.model.CreditCardEntity
import java.util.UUID

data class CreditCard(
    val id: String,
    val name: String,
    val limit: Double,
    val availableLimit: Double,
) {
    companion object {
        fun fakeList() = listOf(
            CreditCard(id = UUID.randomUUID().toString(), name = "Nubank", limit = 0.0, availableLimit = 0.0),
            CreditCard(id = UUID.randomUUID().toString(), name = "Banco do Brasil", limit = 0.0, availableLimit = 0.0),
        )
    }
}

fun CreditCard.asEntity() = CreditCardEntity(
    id = id,
    name = name,
    limit = limit,
    availableLimit = availableLimit
)