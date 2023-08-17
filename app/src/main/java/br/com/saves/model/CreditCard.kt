package br.com.saves.model

import br.com.saves.database.model.CreditCardEntity
import java.util.UUID

data class CreditCard(
    val id: String,
    val issuer: CreditCardIssuer,
    val name: String,
    val limit: Double,
    val availableLimit: Double,
    val dueDay: Int,
) {
    companion object {
        fun fakeList() = listOf(
            CreditCard(id = UUID.randomUUID().toString(), issuer = CreditCardIssuer.NUBANK, name = "Nubank", limit = 0.0, availableLimit = 0.0, 6),
            CreditCard(id = UUID.randomUUID().toString(), issuer = CreditCardIssuer.BB, name = "Banco do Brasil", limit = 0.0, availableLimit = 0.0, 17),
        )
    }
}

fun CreditCard.asEntity() = CreditCardEntity(
    id = id,
    issuer = issuer,
    name = name,
    limit = limit,
    availableLimit = availableLimit,
    dueDay = dueDay
)