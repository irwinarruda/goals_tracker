package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.GoalItemBinding
import kotlinx.coroutines.*

class GoalItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = GoalItemBinding.inflate(LayoutInflater.from(context), this, true)
    private var timer: Job? = null
    private var isDelete = false
    private var onDelete: OnClickListener? = null

    init {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.GoalItem)
            binding.goalItemTitle.text = attributes.getString(R.styleable.GoalItem_goalItem_title) ?: ""
            binding.goalItemDurationValue.text = attributes.getInt(R.styleable.GoalItem_goalItem_duration, 0).toString()
            binding.goalItemDateValue.text = attributes.getString(R.styleable.GoalItem_goalItem_date) ?: ""
            binding.goalItemCoinsValue.text = attributes.getInt(R.styleable.GoalItem_goalItem_coins, 0).toString()
            attributes.recycle()
        }
        binding.root.setOnClickListener {
            if (!isDelete) {
                makeDeleteVisible()
                createDelay()
            } else {
                if (onDelete != null) onDelete!!.onClick(binding.root)
            }
        }
    }

    fun setOnDeleteClick(cb: OnClickListener) {
        onDelete = cb
    }

    private fun makeInfoVisible() {
        binding.goalItemInfoContainer.visibility = View.VISIBLE
        binding.goalItemDeleteContainer.visibility = View.GONE
        binding.root.setBackgroundResource(R.drawable.goal_layer)
        isDelete = false
    }

    private fun makeDeleteVisible() {
        binding.goalItemInfoContainer.visibility = View.GONE
        binding.goalItemDeleteContainer.visibility = View.VISIBLE
        binding.root.setBackgroundResource(R.drawable.goal_deletable_layer)
        isDelete = true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun createDelay() {
        if (timer != null) timer!!.cancel()
        timer = GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            makeInfoVisible()
        }
    }
}
