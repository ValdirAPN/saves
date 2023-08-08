package br.com.saves.model

import br.com.saves.database.model.TransactionEntity
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone
import java.util.UUID

data class Transaction(
    val id: String,
    val description: String,
    val date: LocalDateTime,
    val amount: Double,
    val installments: Int,
    val type: TransactionType,
    val bankAccountId: String? = null,
    val creditCardId: String? = null
) {
    companion object {
        fun fakeList() = listOf(
            Transaction(id = UUID.randomUUID().toString(), description = "Salário - MobApps", date = LocalDateTime.now(), amount = 5500.0, installments = 1, type = TransactionType.INCOME),
            Transaction(id = UUID.randomUUID().toString(),description = "Parcela da Casa", date = LocalDateTime.now(), amount = 890.0, installments = 1, type = TransactionType.EXPENSE),
            Transaction(id = UUID.randomUUID().toString(),description = "Cinema", date = LocalDateTime.now(), amount = 94.73, installments = 1, type = TransactionType.EXPENSE),
        )
    }
}

fun Transaction.asEntity() = TransactionEntity(
    id = id,
    description = description,
    amount = amount,
    installments = installments,
    timestamp = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    type = type,
    bankAccountId = bankAccountId,
    creditCardId = creditCardId
)

enum class TransactionType {
    INCOME, EXPENSE
}
