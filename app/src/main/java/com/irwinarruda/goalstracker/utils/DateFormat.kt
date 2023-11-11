package com.irwinarruda.goalstracker.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormat {
    companion object {
        fun formatDate(selectedDate: Long, mask: String): String {
            val simpleDateFormat = SimpleDateFormat(mask, Locale.getDefault())
            val date = Date(selectedDate)
            return simpleDateFormat.format(date)
        }
    }
}