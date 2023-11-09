package com.irwinarruda.goalstracker.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irwinarruda.goalstracker.entities.DayState

class DayButtonViewModel : ViewModel() {
    private val _dayButton = MutableLiveData<DayButtonModel>(DayButtonModel(DayState.DISABLED))
    val dayButton: LiveData<DayButtonModel> = _dayButton

    private val _day = MutableLiveData<String>("")
    val day: LiveData<String> = _day

    private val _count = MutableLiveData<Int>(-1)
    val count: LiveData<Int> = _count
    fun setDayState(newDayState: DayState? = null) {
        if (newDayState == null) {
            _dayButton.value = _dayButton.value!!
        }
        _dayButton.value = DayButtonModel(newDayState!!)
    }

    fun setDay(newDay: String? = null) {
        if (newDay == null) {
            _day.value = _day.value
        }
        _day.value = newDay!!
    }

    fun setCount(newCount: Int? = null) {
        if (newCount == null) {
            _count.value = _count.value
        }
        _count.value = newCount!!
    }
}