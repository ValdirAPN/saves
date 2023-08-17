package br.com.saves.model

import br.com.saves.database.model.BankAccountEntity
import java.util.UUID

data class BankAccount(
    override val id: String,
    override val institution: Bank,
    override val name: String,
    val balance: Double
): FinanceEntity {
    companion object {
        fun fakeList() = listOf(
            BankAccount(UUID.randomUUID().toString(), Bank.NUBANK, "Nubank", 0.0),
            BankAccount(UUID.randomUUID().toString(), Bank.WILL, "Will", 0.0),
            BankAccount(UUID.randomUUID().toString(), Bank.INTER, "Inter", 0.0),
        )
    }
}

fun BankAccount.asEntity() = BankAccountEntity(
    id = id,
    bank = institution,
    name = name,
    balance = balance
)