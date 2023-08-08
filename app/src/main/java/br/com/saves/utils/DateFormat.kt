package br.com.saves.utils

import android.content.Context
import android.icu.util.Calendar
import android.text.format.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun LocalDateTime.formatDate(): String {
    val formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault())
    val date = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
    return formatter.format(date)
}

fun Date.getRelativeDateTimeString(context: Context): String {
    return DateUtils.getRelativeDateTimeString(
        context,
        this.time,
        DateUtils.DAY_IN_MILLIS,
        DateUtils.WEEK_IN_MILLIS,
        DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME,
    ).toString()
}

fun Long.getDateString(): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@getDateString
    }

    val dateFormat = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).apply { timeZone = TimeZone.getTimeZone("UTC") }

    return dateFormat.format(calendar.time)
}
