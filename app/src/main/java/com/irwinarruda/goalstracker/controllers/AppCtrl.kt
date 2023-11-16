package com.irwinarruda.goalstracker.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irwinarruda.goalstracker.entities.Day
import com.irwinarruda.goalstracker.entities.DayState
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.repositories.GoalsRepository

class AppCtrl(application: Application) : AndroidViewModel(application) {
    private val goalsRepository = GoalsRepository.get(application)

    private val _userCoins = MutableLiveData(30)
    val userCoins: LiveData<Int> = _userCoins

    private val _goals = MutableLiveData(mutableListOf<Goal>())
    val goals: LiveData<MutableList<Goal>> = _goals

    private var _selectedGoalId = MutableLiveData<Int?>(null)
    var selectedGoalId: LiveData<Int?> = _selectedGoalId
    val selectedGoal: Goal?
        get() = _goals.value!!.find { goal -> goal.id == selectedGoalId.value }

    private var _changedDayId = MutableLiveData<Int?>(null)
    var changedDayId: LiveData<Int?> = _changedDayId

    fun start() {
        getUserCoins()
        list()
        selectFirstGoal()
    }

    fun list() {
        val goals = goalsRepository.getAll(true)
        _goals.value = goals
    }

    fun create(goal: Goal) {
        goalsRepository.create(goal)
        list()
        if (_selectedGoalId.value == null) {
            selectFirstGoal()
        }
    }

    fun delete(id: Int) {
        goalsRepository.deleteById(id)
        list()
        if (_selectedGoalId.value == id) {
            selectFirstGoal()
        }
    }

    fun selectFirstGoal() {
        if (_goals.value!!.isEmpty()) {
            _selectedGoalId.value = null
            return
        }
        updateDays(_goals.value!![0].id)
        _selectedGoalId.value = _goals.value!![0].id
    }

    fun selectGoal(goal: Goal) {
        updateDays(goal.id)
        _selectedGoalId.value = goal.id
    }

    fun updatePendingDay(day: Day) {
        goalsRepository.updateDayState(day.id, DayState.SUCCESS)
        list()
        updateUserCoins(_userCoins.value!! + 1)
        _changedDayId.value = day.id
    }

    fun updateGoldDay(day: Day) {
        if (_selectedGoalId.value != null) {
            goalsRepository.updateDayState(day.id, DayState.GOLD)
            list()
            updateUserCoins(_userCoins.value!! - selectedGoal!!.coins!!)
            _changedDayId.value = day.id
        }
    }

    fun resetChangedDayId() {
        _changedDayId.value = null
    }

    private fun updateDays(goalId: Int) {
        goalsRepository.updateDaysByGoalId(goalId)
        list()
    }

    fun getUserCoins() {
        _userCoins.value = goalsRepository.getUserCoins()
    }

    fun updateUserCoins(coins: Int) {
        goalsRepository.updateUserCoins(coins)
        getUserCoins()
    }
}