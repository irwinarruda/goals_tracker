package com.irwinarruda.goalstracker.components

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.screens.GoalsScreen
import com.irwinarruda.goalstracker.screens.TrackerScreen

class TabAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object {
        private fun getTabTitles(context: Context): Array<String> {
            return arrayOf(
                context.getString(R.string.tabTitle_tracker),
                context.getString(R.string.tabTitle_goals)
            )
        }

        fun createLayoutMediator(context: Context, tabs: TabLayout, tabPanel: ViewPager2): TabLayoutMediator {
            val tabLayoutMediator = TabLayoutMediator(tabs, tabPanel) { tab, position ->
                tab.text = getTabTitles(context)[position]
            }
            tabLayoutMediator.attach()
            return tabLayoutMediator
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrackerScreen()
            1 -> GoalsScreen()
            else -> throw Exception(
                "Wrong position"
            )
        }
    }
}