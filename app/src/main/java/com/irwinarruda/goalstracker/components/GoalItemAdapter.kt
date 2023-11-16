package com.irwinarruda.goalstracker.components

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.utils.Sizes
import com.irwinarruda.goalstracker.utils.formatMask

class GoalItemAdapter(
    private val goals: List<Goal>,
    private val onDelete: (goal: Goal) -> Unit
) : RecyclerView.Adapter<GoalItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: GoalItem) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val goalItem = GoalItem(parent.context, null)
        return ViewHolder(goalItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goal = goals[position]
        val goalItem = holder.itemView as GoalItem
        goalItem.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        if (position > 0) {
            (goalItem.layoutParams as LinearLayout.LayoutParams).topMargin = Sizes.dpToPixels(10)
        }
        goalItem.setTitle(goal.description)
        goalItem.setDuration(goal.dayCount)
        goalItem.setDate(goal.startDate.formatMask("dd/MM/yyyy"))
        goalItem.setCoins(goal.coins)
        goalItem.setOnDeleteClick {
            onDelete(goal)
        }
    }

    override fun getItemCount() = goals.size
}
