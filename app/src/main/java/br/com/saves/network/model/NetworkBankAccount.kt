package br.com.saves.network.model

import br.com.saves.database.model.BankAccountEntity

data class NetworkBankAccount(
    val id: String,
    val name: String,
    val balance: Double
)

fun NetworkBankAccount.asEntity() = BankAccountEntity(
    id = id,
    name = name,
    balance = balance
)