package br.com.saves.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.saves.model.CreditCard
import br.com.saves.model.CreditCardIssuer

@Entity(
    tableName = "credit_cards"
)
data class CreditCardEntity(
    @PrimaryKey
    val id: String,
    val issuer: CreditCardIssuer,
    val name: String,
    val limit: Double,
    val availableLimit: Double,
    val dueDay: Int,
)

fun CreditCardEntity.asModel() = CreditCard(
    id = id,
    institution = issuer,
    name = name,
    limit = limit,
    availableLimit = availableLimit,
    dueDay = dueDay
)