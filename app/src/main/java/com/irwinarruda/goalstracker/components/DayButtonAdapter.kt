package com.irwinarruda.goalstracker.components

import android.content.Context
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.irwinarruda.goalstracker.entities.Day
import com.irwinarruda.goalstracker.entities.DayState
import com.irwinarruda.goalstracker.utils.Sizes
import com.irwinarruda.goalstracker.utils.WeekDayFormat
import com.irwinarruda.goalstracker.utils.formatMask

class DayButtonAdapter(
    private val context: Context,
    private val days: List<Day>,
    private val onPending: (day: Day) -> Unit
) : RecyclerView.Adapter<DayButtonAdapter.ViewHolder>() {

    class ViewHolder(itemView: DayButton) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val goalItem = DayButton(parent.context, null)
        return ViewHolder(goalItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        val dayButton = holder.itemView as DayButton
        dayButton.layoutParams = RelativeLayout.LayoutParams(
            Sizes.dpToPixels(60),
            Sizes.dpToPixels(60),
        )
        if (position > 4) {
            (dayButton.layoutParams as RelativeLayout.LayoutParams).topMargin = Sizes.dpToPixels(6)
        }

        dayButton.setCount(day.count + 1)
        dayButton.setDay(WeekDayFormat.dayOfTheWeekToString(context, day.date.dayOfWeek))
        dayButton.setDate(day.date.formatMask("dd/MM"))
        if (day.state == DayState.PENDING) {
            dayButton.setOnCLickListener { onPending(day) }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val dayButton = holder.itemView as DayButton
        if (payloads.isNotEmpty()) {
            dayButton.setDayState(payloads[0] as DayState)
            days[position].state = payloads[0] as DayState
        } else {
            dayButton.setDayState(days[position].state)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount() = days.size
}
