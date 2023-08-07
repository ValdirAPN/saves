package br.com.saves.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.saves.model.Transaction
import br.com.saves.model.TransactionType
import java.util.Date

@Entity(
    tableName = "transactions"
)
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val amount: Double,
    val installments: Int,
    val type: TransactionType,
    val timestamp: Long,
    val bankAccountId: String?,
    val creditCardId: String?
)

fun TransactionEntity.asModel() = Transaction(
    id = id,
    description = description,
    date = Date(timestamp),
    amount = amount,
    installments = installments,
    type = type,
    bankAccountId = bankAccountId,
    creditCardId = creditCardId
)