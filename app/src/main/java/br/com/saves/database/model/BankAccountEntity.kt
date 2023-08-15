package br.com.saves.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.saves.model.Bank
import br.com.saves.model.BankAccount

@Entity(
    tableName = "bank_accounts"
)
data class BankAccountEntity(
    @PrimaryKey
    val id: String,
    val bank: Bank,
    val name: String,
    val balance: Double,
)

fun BankAccountEntity.asModel() = BankAccount(
    id = id,
    bank = bank,
    name = name,
    balance = balance
)