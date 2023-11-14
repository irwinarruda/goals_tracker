package com.irwinarruda.goalstracker.entities

import java.time.LocalDate

class Goal(
    var description: String,
    var dayCount: Int,
    var startDate: LocalDate,
    var coins: Int?,
) {
    var id: Int = 0
    var days: List<Day> = listOf()

    constructor(
        id: Int,
        description: String,
        dayCount: Int,
        startDate: LocalDate,
        coins: Int?,
    ) : this(description, dayCount, startDate, coins) {
        this.id = id
    }

    constructor(
        id: Int,
        description: String,
        dayCount: Int,
        startDate: LocalDate,
        coins: Int?,
        days: List<Day>
    ) : this(description, dayCount, startDate, coins) {
        this.id = id
        this.days = days
    }
}