package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.DayButtonBinding
import com.irwinarruda.goalstracker.entities.DayState

class DayButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private var dayButtonViewModel: DayButtonViewModel = DayButtonViewModel()
    private var binding = DayButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs?.let { attributeSet ->
            val styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayButton)
            dayButtonViewModel.setDay(styledAttributes.getString(R.styleable.DayButton_dayButton_day) ?: "")
            dayButtonViewModel.setCount(styledAttributes.getInt(R.styleable.DayButton_dayButton_count, 0))
            styledAttributes.recycle()
        }
    }

    fun setDayState(newDayState: DayState) = dayButtonViewModel.setDayState(newDayState)
    fun setDay(newDay: String) = dayButtonViewModel.setDay(newDay)
    fun setCount(newCount: Int) = dayButtonViewModel.setCount(newCount)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        dayButtonViewModel.dayButton.observe(findViewTreeLifecycleOwner()!!) {
            binding.root.setBackgroundResource(it.background)
            binding.root.isClickable = !it.isDisabled
            binding.root.isFocusable = !it.isDisabled
        }

        dayButtonViewModel.day.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonDay.text = it.toString()
        }

        dayButtonViewModel.count.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonCount.text = it.toString()
        }
    }
}
