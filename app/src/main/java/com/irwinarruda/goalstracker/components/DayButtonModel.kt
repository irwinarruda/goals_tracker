package com.irwinarruda.goalstracker.components

import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.entities.DayState


class DayButtonModel(private val state: DayState) {
    var background = -1
    var isDisabled = false

    init {
        setUI()
    }

    private fun setUI() {
        when (state) {
            DayState.DISABLED -> {
                background = R.drawable.card_layer_disabled
                isDisabled = true
            }

            DayState.PENDING -> {
                background = R.drawable.card_layer_pending
                isDisabled = false
            }

            DayState.SUCCESS -> {
                background = R.drawable.card_layer_success
                isDisabled = false
            }

            DayState.ERROR -> {
                background = R.drawable.card_layer_error
                isDisabled = false
            }

            DayState.GOLD -> {
                background = R.drawable.card_layer_gold
                isDisabled = false
            }
        }
    }
}
