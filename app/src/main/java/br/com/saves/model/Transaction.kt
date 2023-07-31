package br.com.saves.model

import java.util.Date

data class Transaction(
    val title: String,
    val date: Date,
    val value: Double,
    val type: TransactionType
) {
    companion object {
        fun fakeList() = listOf(
            Transaction(title = "Salário - MobApps", date = Date(), value = 5.500, type = TransactionType.INCOME),
            Transaction(title = "Parcela da Casa", date = Date(), value = 890.0, type = TransactionType.EXPENSE),
            Transaction(title = "Cinema", date = Date(), value = 94.73, type = TransactionType.EXPENSE),
        )
    }
}

enum class TransactionType {
    INCOME, EXPENSE
}
