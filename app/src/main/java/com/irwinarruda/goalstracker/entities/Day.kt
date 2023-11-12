package com.irwinarruda.goalstracker.entities

import java.time.LocalDate

class Day(
    val id: Int,
    val count: Int,
    val date: LocalDate,
    val state: DayState
)