package com.irwinarruda.goalstracker.entities

import java.time.LocalDate

class Goal(
    val description: String,
    val dayCount: Int,
    val startDate: LocalDate,
    val coins: Int?,
) {
    var id: Int = 0
    var days: MutableList<Day> = mutableListOf()

    constructor(
        id: Int,
        description: String,
        dayCount: Int,
        startDate: LocalDate,
        coins: Int?,
    ) : this(description, dayCount, startDate, coins) {
        this.id = id
    }
}