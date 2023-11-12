package com.irwinarruda.goalstracker.entities

import java.util.*

class Goal(
    val description: String,
    val dayCount: Int,
    val startDate: Date,
    val coins: Int?,
) {
    val id: Int = 0
    val days: Array<Day> = arrayOf()
}