package br.com.saves.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency(): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return currencyFormat.format(this)
}