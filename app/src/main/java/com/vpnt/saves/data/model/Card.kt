package com.vpnt.saves.data.model

import java.util.Date

data class CreditCard(
    val id: String,
    val name: String,
    val availableLimit: Double,
    val invoice: Double,
    val expiration: Date
) {
    companion object {
        fun fakeList() = listOf(
            CreditCard(
                id = "1",
                name = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            ),
            CreditCard(
                id = "2",
                name = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            ),
            CreditCard(
                id = "3",
                name = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            ),
            CreditCard(
                id = "4",
                name = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            )
        )
    }
}