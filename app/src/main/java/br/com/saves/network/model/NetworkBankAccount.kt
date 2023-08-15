package br.com.saves.network.model

import br.com.saves.database.model.BankAccountEntity
import br.com.saves.model.Bank

data class NetworkBankAccount(
    val id: String,
    val bank: Bank,
    val name: String,
    val balance: Double
)

fun NetworkBankAccount.asEntity() = BankAccountEntity(
    id = id,
    bank = bank,
    name = name,
    balance = balance
)