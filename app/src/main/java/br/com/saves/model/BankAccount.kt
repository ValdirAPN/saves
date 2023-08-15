package br.com.saves.model

import br.com.saves.database.model.BankAccountEntity
import java.util.UUID

data class BankAccount(
    val id: String,
    val bank: Bank,
    val name: String,
    val balance: Double
) {
    companion object {
        fun fakeList() = listOf(
            BankAccount(UUID.randomUUID().toString(), Bank.NUBANK, "Nubank", 0.0),
            BankAccount(UUID.randomUUID().toString(), Bank.WILL, "Will", 0.0),
            BankAccount(UUID.randomUUID().toString(), Bank.CITI, "Inter", 0.0),
        )
    }
}

fun BankAccount.asEntity() = BankAccountEntity(
    id = id,
    bank = bank,
    name = name,
    balance = balance
)