package com.irwinarruda.goalstracker.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toEpochMilli(): Long {
    return this.toEpochDay() * 24 * 60 * 60 * 1000
}

fun LocalDate.formatMask(mask: String): String {
    val formatter = DateTimeFormatter.ofPattern(mask)
    return this.format(formatter)
}