package com.irwinarruda.goalstracker.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.IconButtonBinding

class IconButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = IconButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    override fun setOnClickListener(cb: OnClickListener?) {
        binding.iconButtonContainer.setOnClickListener(cb)
    }

    private fun setIcon(icon: Drawable) {
        binding.iconButtonIcon.setImageDrawable(icon)
    }

    private fun setIconSize(size: Int) {
        binding.iconButtonIcon.layoutParams.width = size
        binding.iconButtonIcon.layoutParams.height = size
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.IconButton)

            val attrIcon = attributes.getDrawable(R.styleable.IconButton_iconButton_icon)
            if (attrIcon != null) setIcon(attrIcon)

            val attrIconSize = attributes.getLayoutDimension(R.styleable.IconButton_iconButton_iconSize, -1)
            if (attrIconSize != -1) setIconSize(attrIconSize)

            attributes.recycle()
        }
    }
}