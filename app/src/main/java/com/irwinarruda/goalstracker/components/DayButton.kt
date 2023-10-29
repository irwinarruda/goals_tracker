package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.DayButtonBinding

class DayButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private val binding = DayButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var count = 0
    private var day = ""

    init {
        setLayout(attrs)
    }

    private fun setCount(newCount: Int) {
        binding.dayButtonCount.text = newCount.toString()
        count = newCount
    }

    private fun setDay(newDay: String) {
        binding.dayButtonDay.text = newDay
        day = newDay
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayButton)

            val attrCount = attributes.getInt(R.styleable.DayButton_dayButton_count, 0)
            setCount(attrCount)

            val attrDay = attributes.getString(R.styleable.DayButton_dayButton_day)
            setDay(attrDay ?: "")

            attributes.recycle()
        }
    }

    enum class DayButtonState {
        DISABLED,
        PENDING,
        SUCCESS,
        FAIL,
        GOLD
    }
}