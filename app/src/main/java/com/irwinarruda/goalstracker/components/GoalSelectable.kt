package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.GoalSelectableBinding

class GoalSelectable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = GoalSelectableBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.GoalSelectable)
            binding.goalSelectableTitle.text =
                attributes.getString(R.styleable.GoalSelectable_goalSelectable_title) ?: ""
            binding.goalSelectableDurationValue.text =
                attributes.getInt(R.styleable.GoalSelectable_goalSelectable_duration, 0).toString()
            binding.goalSelectableDateValue.text =
                attributes.getString(R.styleable.GoalSelectable_goalSelectable_date) ?: ""
            binding.goalSelectableCoinsValue.text =
                attributes.getInt(R.styleable.GoalSelectable_goalSelectable_coins, 0).toString()
            attributes.recycle()
        }
    }
}