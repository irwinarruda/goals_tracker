package com.irwinarruda.goalstracker.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.repositories.GoalsRepository
import java.util.*

class GoalsScreenCtrl(application: Application) : AndroidViewModel(application) {
    private val goalsRepository = GoalsRepository.get(application)
    fun create() {
        goalsRepository.create(Goal("Test", 30, Date(), 7))
    }

    fun list() {
        goalsRepository.getAll()
    }
}