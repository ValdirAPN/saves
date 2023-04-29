package com.vpnt.saves.data.model

import java.util.Date

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val date: Date
) {
    companion object {
        fun fakeList() = listOf<Transaction>(
            Transaction(
                id = "1",
                title = "Gasolina",
                amount = 100.00,
                date = Date()
            ),
            Transaction(
                id = "2",
                title = "Rodízio de petiscos",
                amount = 45.00,
                date = Date()
            ),
            Transaction(
                id = "3",
                title = "Gasolina",
                amount = 192.00,
                date = Date()
            ),
            Transaction(
                id = "4",
                title = "Gasolina",
                amount = 100.00,
                date = Date()
            ),
            Transaction(
                id = "5",
                title = "Gasolina",
                amount = 100.00,
                date = Date()
            ),
            Transaction(
                id = "6",
                title = "Gasolina",
                amount = 100.00,
                date = Date()
            ),
            Transaction(
                id = "7",
                title = "Gasolina",
                amount = 100.00,
                date = Date()
            ),
            Transaction(
                id = "8",
                title = "Gasolina",
                amount = 100.00,
                date = Date()
            )
        )
    }
}