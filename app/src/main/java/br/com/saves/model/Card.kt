package br.com.saves.model

import java.util.UUID

data class Card(
    val id: String,
    val name: String,
    val limit: Double
) {
    companion object {
        fun fakeList() = listOf(
            Card(id = UUID.randomUUID().toString(), name = "Nubank", limit = 0.0),
            Card(id = UUID.randomUUID().toString(), name = "Banco do Brasil", limit = 0.0),
        )
    }
}
