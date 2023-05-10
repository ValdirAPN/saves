package com.vpnt.saves.data.model

import java.util.Date

data class CreditCard(
    val id: String,
    val title: String,
    val availableLimit: Double,
    val invoice: Double,
    val expiration: Date
) {
    companion object {
        fun fakeList() = listOf(
            CreditCard(
                id = "1",
                title = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            ),
            CreditCard(
                id = "2",
                title = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            ),
            CreditCard(
                id = "3",
                title = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            ),
            CreditCard(
                id = "4",
                title = "Nubank",
                availableLimit = 3000.00,
                invoice = 2599.00,
                expiration = Date()
            )
        )
    }
}