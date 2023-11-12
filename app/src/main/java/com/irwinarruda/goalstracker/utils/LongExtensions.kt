package com.irwinarruda.goalstracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class DateFormat {
}
fun Long.formatDate(mask: String): String {
    val simpleDateFormat = SimpleDateFormat(mask, Locale.getDefault())
    val date = Date(this)
    return simpleDateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}
