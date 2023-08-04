package br.com.saves.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency(): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return currencyFormat.format(this)
}

fun Double.toNumberFormat(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    return numberFormat.format(this)
}