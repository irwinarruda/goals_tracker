package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.DayButtonBinding
import com.irwinarruda.goalstracker.entities.DayState

class DayButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private var binding = DayButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs?.let { attributeSet ->
            val styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayButton)
            setDay(styledAttributes.getString(R.styleable.DayButton_dayButton_day) ?: "")
            setCount(styledAttributes.getInt(R.styleable.DayButton_dayButton_count, 0))
            setDate(styledAttributes.getString(R.styleable.DayButton_dayButton_date) ?: "")
            setDayState(DayState.DISABLED)
            styledAttributes.recycle()
        }
    }

    fun setOnCLickListener(cb: OnClickListener) {
        binding.root.setOnClickListener(cb)
    }

    fun setDayState(newDayState: DayState) {
        val dayModel = DayButtonModel(newDayState)
        binding.root.setBackgroundResource(dayModel.background)
        binding.root.isClickable = !dayModel.isDisabled
        binding.root.isFocusable = !dayModel.isDisabled
    }

    fun setDay(newDay: String) {
        binding.dayButtonDay.text = newDay
    }

    fun setDate(newDate: String) {
        binding.dayButtonDate.text = newDate
    }

    fun setCount(newCount: Int) {
        binding.dayButtonCount.text = newCount.toString()
    }

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
                    isDisabled = false
                }

                DayState.PENDING -> {
                    background = R.drawable.card_layer_pending
                    isDisabled = false
                }

                DayState.SUCCESS -> {
                    background = R.drawable.card_layer_success
                    isDisabled = true
                }

                DayState.ERROR -> {
                    background = R.drawable.card_layer_error
                    isDisabled = true
                }

                DayState.GOLD -> {
                    background = R.drawable.card_layer_gold
                    isDisabled = true
                }
            }
        }
    }
}
