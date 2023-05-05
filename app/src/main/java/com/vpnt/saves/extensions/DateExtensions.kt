package com.vpnt.saves.extensions

import java.text.DateFormat
import java.util.Date

fun Date.format(): String {
    return DateFormat.getDateTimeInstance().format(this)
}