package br.com.saves.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(): String {
    val formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault())
    return formatter.format(this)
}