package br.com.saves.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.saves.model.CreditCard

@Entity(
    tableName = "credit_cards"
)
data class CreditCardEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val limit: Double,
    val availableLimit: Double,
)

fun CreditCardEntity.asModel() = CreditCard(
    id = id,
    name = name,
    limit = limit,
    availableLimit = availableLimit
)