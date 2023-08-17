package br.com.saves.model

import br.com.saves.database.model.CreditCardEntity
import java.util.UUID

data class CreditCard(
    override val id: String,
    override val institution: CreditCardIssuer,
    override val name: String,
    val limit: Double,
    val availableLimit: Double,
    val dueDay: Int,
): FinanceEntity {
    companion object {
        fun fakeList() = listOf(
            CreditCard(id = UUID.randomUUID().toString(), institution = CreditCardIssuer.NUBANK, name = "Nubank", limit = 0.0, availableLimit = 0.0, 6),
            CreditCard(id = UUID.randomUUID().toString(), institution = CreditCardIssuer.BB, name = "Banco do Brasil", limit = 0.0, availableLimit = 0.0, 17),
        )
    }
}

fun CreditCard.asEntity() = CreditCardEntity(
    id = id,
    issuer = institution,
    name = name,
    limit = limit,
    availableLimit = availableLimit,
    dueDay = dueDay
)