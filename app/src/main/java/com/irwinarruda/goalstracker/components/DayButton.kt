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
    private var attrDay = ""
    private var attrCount = -1

    init {
        attrs?.let { attributeSet ->
            val styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayButton)
            attrDay = styledAttributes.getString(R.styleable.DayButton_dayButton_day) ?: ""
            attrCount = styledAttributes.getInt(R.styleable.DayButton_dayButton_count, 0)
            styledAttributes.recycle()
        }
    }

    fun setDayState(newDayState: DayState) = dayButtonViewModel.setDayState(newDayState)
    fun setDay(newDay: String) = dayButtonViewModel.setDay(newDay)
    fun setCount(newCount: Int) = dayButtonViewModel.setCount(newCount)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        dayButtonViewModel.dayButton.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonBackground.setBackgroundResource(it.background)
            binding.dayButtonContainer.isClickable = !it.isDisabled
            binding.dayButtonContainer.isFocusable = !it.isDisabled
        }

        dayButtonViewModel.day.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonDay.text = it.toString()
        }

        dayButtonViewModel.count.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonCount.text = it.toString()
        }

        setCount(attrCount)
        setDay(attrDay)
        setDayState(DayState.DISABLED)
    }
}
