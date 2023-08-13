package br.com.saves.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.saves.database.model.BankAccountEntity
import br.com.saves.database.model.CreditCardEntity
import java.util.UUID

data class BankAccount(
    val id: String,
    val name: String,
    val balance: Double
) {
    companion object {
        fun fakeList() = listOf(
            BankAccount(UUID.randomUUID().toString(), "Nubank", 0.0),
            BankAccount(UUID.randomUUID().toString(), "Will", 0.0),
            BankAccount(UUID.randomUUID().toString(), "Inter", 0.0),
        )
    }
}

fun BankAccount.asEntity() = BankAccountEntity(
    id = id,
    name = name,
    balance = balance
)