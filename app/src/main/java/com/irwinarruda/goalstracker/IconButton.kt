package com.irwinarruda.goalstracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.databinding.IconButtonBinding

class IconButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = IconButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.IconButton)
            // val attrTitle = attributes.getString(R.styleable);
            binding.iconButtonContainer.setOnClickListener {
                Log.show(context, "Here man")
            }
            attributes.recycle()
        }
    }
}