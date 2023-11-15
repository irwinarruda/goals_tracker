package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.components.DayButtonAdapter
import com.irwinarruda.goalstracker.databinding.FragmentTrackerScreenBinding
import com.irwinarruda.goalstracker.databinding.GoalsChangeModalBinding

class TrackerScreen : Fragment(R.layout.fragment_tracker_screen) {
    private lateinit var binding: FragmentTrackerScreenBinding
    private lateinit var goalsModalBinding: GoalsChangeModalBinding
    private lateinit var trackerScreenCtrl: TrackerScreenCtrl
    private var modal: BottomSheetDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTrackerScreenBinding.inflate(inflater, container, false)
        trackerScreenCtrl = ViewModelProvider(requireActivity())[TrackerScreenCtrl::class.java]

        val buttonChangeGoal = binding.trackerScreenChangeGoal
        buttonChangeGoal.setOnClickListener { onChangeGoalsClick() }
        createObservables()
        trackerScreenCtrl.list()
        trackerScreenCtrl.selectFirstGoal()
        return binding.root
    }

    private fun createObservables() {
        trackerScreenCtrl.selectedGoal.observe(viewLifecycleOwner) { goal ->
            if (goal != null) {
                val trackerScreenDaysList = binding.trackerScreenDaysList
                trackerScreenDaysList.setHasFixedSize(true)
                trackerScreenDaysList.layoutManager =
                    GridLayoutManager(requireContext(), 5, GridLayoutManager.VERTICAL, false)
                trackerScreenDaysList.adapter = DayButtonAdapter(requireContext(), goal.days) { day ->
                    trackerScreenCtrl.updatePendingDay(day)
                }
                binding.trackerScreenTitle.text = goal.description
            }
        }
        trackerScreenCtrl.goals.observe(viewLifecycleOwner) {
        }
    }

    private fun onChangeGoalsClick() {
        if (modal == null) {
            goalsModalBinding = GoalsChangeModalBinding.inflate(layoutInflater, null, false)
            modal = BottomSheetDialog(requireContext(), R.style.ThemeGoalsModal)
            modal!!.setContentView(goalsModalBinding.root)
            goalsModalBinding.goalChangeModalButtonClose.setOnClickListener { modal!!.dismiss() }
        }
        modal!!.show()
    }
}