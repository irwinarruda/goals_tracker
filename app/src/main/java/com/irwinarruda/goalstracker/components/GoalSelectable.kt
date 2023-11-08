package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.databinding.GoalSelectableBinding

class GoalSelectable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = GoalSelectableBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // setLayout(attrs)
    }

    // private fun setLayout(attrs: AttributeSet?) {
    //     attrs?.let { attributeSet ->
    //         val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.)
    //         attributes.recycle()
    //    }
    // }
}