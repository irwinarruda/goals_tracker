package com.irwinarruda.goalstracker.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irwinarruda.goalstracker.entities.Day
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.repositories.GoalsRepository

class TrackerScreenCtrl(application: Application) : AndroidViewModel(application) {
    private val goalsRepository = GoalsRepository.get(application)
    private val _goals = MutableLiveData<MutableList<Goal>>(mutableListOf())
    val goals: LiveData<MutableList<Goal>> = _goals
    private val _selectedGoal = MutableLiveData<Goal?>(null)
    val selectedGoal: LiveData<Goal?> = _selectedGoal
    private var selectedGoalId: Int = -1

    fun list() {
        val goals = goalsRepository.getAll(true)
        _goals.value = goals
    }

    fun selectFirstGoal() {
        if (_goals.value!!.isEmpty()) return
        _selectedGoal.value = _goals.value!![0]
        selectedGoalId = _selectedGoal.value!!.id
        updateDays()
    }

    fun updatePendingDay(day: Day) {
        goalsRepository.updatePendingDay(day.id)
        list()
        _selectedGoal.value = _goals.value!!.find { goal -> goal.id == selectedGoalId }
    }

    fun updateDays() {
        goalsRepository.updateDaysByGoalId(selectedGoalId)
        list()
    }
}