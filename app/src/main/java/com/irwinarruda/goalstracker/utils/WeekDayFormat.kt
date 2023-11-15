package com.irwinarruda.goalstracker.utils

import android.content.Context
import com.irwinarruda.goalstracker.R
import java.time.DayOfWeek

class WeekDayFormat {
    companion object {
        fun dayOfTheWeekToString(context: Context, dayOfWeek: DayOfWeek): String {
            return when (dayOfWeek) {
                DayOfWeek.MONDAY -> context.getString(R.string.dayOfWeek_monday)
                DayOfWeek.TUESDAY -> context.getString(R.string.dayOfWeek_tuesday)
                DayOfWeek.WEDNESDAY -> context.getString(R.string.dayOfWeek_wednesday)
                DayOfWeek.THURSDAY -> context.getString(R.string.dayOfWeek_thursday)
                DayOfWeek.FRIDAY -> context.getString(R.string.dayOfWeek_friday)
                DayOfWeek.SATURDAY -> context.getString(R.string.dayOfWeek_saturday)
                DayOfWeek.SUNDAY -> context.getString(R.string.dayOfWeek_sunday)
                else -> ""
            }
        }
    }
}