package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.lifecycle.*
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.DayButtonBinding
import com.irwinarruda.goalstracker.entities.DayState

class DayButton @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet?,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private val dayButtonViewModel by lazy {
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!).get<DayButtonViewModel>()
    }

    private val binding = DayButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private val lifecycleOwner: LifecycleOwner
        get() {
            return findViewTreeLifecycleOwner()!!
        }

    init {
    }

    fun setDayState(newDayState: DayState) = dayButtonViewModel.setDayState(newDayState)
    fun setDay(newDay: String) = dayButtonViewModel.setDay(newDay)
    fun setCount(newCount: Int) = dayButtonViewModel.setCount(newCount)

    private fun startLayout() {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.DayButton)

            val attrCount = attributes.getInt(R.styleable.DayButton_dayButton_count, 0)
            setCount(attrCount)

            val attrDay = attributes.getString(R.styleable.DayButton_dayButton_day)
            setDay(attrDay ?: "")

            attributes.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        dayButtonViewModel.dayButton.observe(findViewTreeLifecycleOwner()!!) {
        }

        dayButtonViewModel.day.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonDay.text = it
        }

        dayButtonViewModel.count.observe(findViewTreeLifecycleOwner()!!) {
            binding.dayButtonCount.text = it.toString()
        }

        startLayout()
    }
}