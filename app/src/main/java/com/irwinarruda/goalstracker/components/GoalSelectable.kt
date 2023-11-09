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
    private var title = ""
    private var duration = 0
    private var date = ""
    private var coins = 0

    init {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.GoalSelectable)
            title = attributes.getString(R.styleable.GoalSelectable_goalSelectable_title) ?: ""
            duration = attributes.getInt(R.styleable.GoalSelectable_goalSelectable_duration, 0)
            date = attributes.getString(R.styleable.GoalSelectable_goalSelectable_date) ?: ""
            coins = attributes.getInt(R.styleable.GoalSelectable_goalSelectable_coins, 0)
            attributes.recycle()
        }
        setAttributes()
    }

    private fun setAttributes() {
        binding.goalSelectableTitle.text = title
        binding.goalSelectableDurationValue.text = duration.toString()
        binding.goalSelectableDateValue.text = date
        binding.goalSelectableCoinsValue.text = duration.toString()
    }
}