package com.irwinarruda.goalstracker.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun Long.formatDate(mask: String): String {
    val simpleDateFormat = SimpleDateFormat(mask, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = Date(this)
    return simpleDateFormat.format(date)
}

fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneId.of("UTC")).toLocalDate()
}
