package com.irwinarruda.goalstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.irwinarruda.goalstracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tabLayoutMediator = TabLayoutMediator(binding.tabs!!, binding.tabPanel!!) { tab, position ->
            when (position) {
                0 -> tab.text = "Tracker"
                1 -> tab.text = "Goals"
            }
        }
        binding.tabPanel!!.adapter = MyAdapter(this)
        tabLayoutMediator.attach()
    }

    class MyAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
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

    class ViewPagerFragment : Fragment() {}
}