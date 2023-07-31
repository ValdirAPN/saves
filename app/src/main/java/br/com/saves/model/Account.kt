package br.com.saves.model

import java.util.UUID

data class Account(
    val id: String,
    val name: String,
    val balance: Double
) {
    companion object {
        fun fakeList() = listOf(
            Account(id = UUID.randomUUID().toString(), name = "Carteira", balance = 0.0),
            Account(id = UUID.randomUUID().toString(), name = "Nubank", balance = 0.0),
            Account(id = UUID.randomUUID().toString(), name = "Banco do Brasil", balance = 0.0),
        )
    }
}
