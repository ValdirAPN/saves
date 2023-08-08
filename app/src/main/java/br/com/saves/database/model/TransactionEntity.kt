package br.com.saves.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.saves.model.Transaction
import br.com.saves.model.TransactionType
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

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
    date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId()),
    amount = amount,
    installments = installments,
    type = type,
    bankAccountId = bankAccountId,
    creditCardId = creditCardId
)