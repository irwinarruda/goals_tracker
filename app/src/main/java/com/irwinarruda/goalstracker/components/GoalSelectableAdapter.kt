package com.irwinarruda.goalstracker.components

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.utils.Sizes
import com.irwinarruda.goalstracker.utils.formatMask

class GoalSelectableAdapter(
    private val goals: List<Goal>,
    private val selectedGoalId: Int?,
    private val onClick: (Goal) -> Unit
) : RecyclerView.Adapter<GoalSelectableAdapter.ViewHolder>() {

    class ViewHolder(itemView: GoalSelectable) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val goalItem = GoalSelectable(parent.context, null)
        return ViewHolder(goalItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goal = goals[position]
        val goalSelectable = holder.itemView as GoalSelectable
        goalSelectable.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        if (position > 0) {
            (goalSelectable.layoutParams as LinearLayout.LayoutParams).topMargin = Sizes.dpToPixels(10)
        }
        goalSelectable.setTitle(goal.description)
        goalSelectable.setDuration(goal.dayCount)
        goalSelectable.setDate(goal.startDate.formatMask("dd/MM/yyyy"))
        goalSelectable.setCoins(goal.coins)
        goalSelectable.setIsSelected(selectedGoalId == goal.id)
        if (selectedGoalId != goal.id) {
            goalSelectable.setOnClickListener { onClick(goal) }
        }
    }

    override fun getItemCount() = goals.size
}
