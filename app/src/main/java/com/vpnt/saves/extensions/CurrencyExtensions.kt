package com.vpnt.saves.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}