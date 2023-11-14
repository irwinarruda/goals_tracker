package com.irwinarruda.goalstracker.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.repositories.GoalsRepository
import java.time.LocalDate

class GoalsScreenCtrl(application: Application) : AndroidViewModel(application) {
    private val goalsRepository = GoalsRepository.get(application)
    private val _goalsList = MutableLiveData<MutableList<Goal>>(mutableListOf())
    val goalsList: LiveData<MutableList<Goal>> = _goalsList

    fun list() {
        val goals = goalsRepository.getAll()
        val currentDate = LocalDate.now()
        _goalsList.value = goals
    }

    fun create(goal: Goal) {
        goalsRepository.create(goal)
        list()
    }

    fun delete(id: Int) {
        goalsRepository.deleteById(id)
        list()
    }

    fun updateDays(id: Int) {
        goalsRepository.updateDaysByGoalId(id)
        list()
    }
}