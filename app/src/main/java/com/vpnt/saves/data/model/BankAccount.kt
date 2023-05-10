package com.vpnt.saves.data.model

data class BankAccount(
    val id: String,
    val name: String,
    val balance: Double
) {
    companion object {
        fun fakeList() = listOf(
            BankAccount("1", "Nubank", 1000.0),
            BankAccount("2", "Will", 137.45),
            BankAccount("3", "Banco do Brasil", 0.0),
        )
    }
}