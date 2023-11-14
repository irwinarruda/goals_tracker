package com.irwinarruda.goalstracker.entities

import java.time.LocalDate

data class Day(
    var id: Int,
    var goalId: Int,
    var count: Int,
    var date: LocalDate,
    var state: DayState
)