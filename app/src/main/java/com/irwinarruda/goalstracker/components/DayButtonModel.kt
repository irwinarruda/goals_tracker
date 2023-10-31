package com.irwinarruda.goalstracker.components

import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.entities.DayState


class DayButtonModel(val state: DayState) {
    var backgroundColor: Int = -1
    var borderColor: Int? = null
    var isDisabled: Boolean = false

    init {
        setUI()
    }

    private fun setUI() {
        when (state) {
            DayState.DISABLED -> {
                backgroundColor = R.color.gray_400
                borderColor = null
                isDisabled = true
            }

            DayState.PENDING -> {
                backgroundColor = R.color.gray_400
                borderColor = R.color.primary_light
                isDisabled = false
            }

            DayState.SUCCESS -> {
                backgroundColor = R.color.green_500
                borderColor = null
                isDisabled = false
            }

            DayState.FAIL -> {
                backgroundColor = R.color.red_500
                borderColor = null
                isDisabled = false
            }

            DayState.GOLD -> {
                backgroundColor = R.color.green_500
                borderColor = R.color.gold_500
                isDisabled = false
            }
        }
    }
}
