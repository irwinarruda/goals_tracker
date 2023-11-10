package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.GoalItemBinding

class GoalItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = GoalItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var title = ""
    private var duration = 0
    private var date = ""
    private var coins = 0

    init {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.GoalItem)
            title = attributes.getString(R.styleable.GoalItem_goalItem_title) ?: ""
            duration = attributes.getInt(R.styleable.GoalItem_goalItem_duration, 0)
            date = attributes.getString(R.styleable.GoalItem_goalItem_date) ?: ""
            coins = attributes.getInt(R.styleable.GoalItem_goalItem_coins, 0)
            attributes.recycle()
        }
        setAttributes()
    }

    private fun setAttributes() {
        binding.goalItemTitle.text = title
        binding.goalItemDurationValue.text = duration.toString()
        binding.goalItemDateValue.text = date
        binding.goalItemCoinsValue.text = duration.toString()
    }
}
