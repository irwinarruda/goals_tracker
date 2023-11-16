package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.components.DayButtonAdapter
import com.irwinarruda.goalstracker.components.GoalSelectableAdapter
import com.irwinarruda.goalstracker.controllers.AppCtrl
import com.irwinarruda.goalstracker.databinding.FragmentTrackerScreenBinding
import com.irwinarruda.goalstracker.databinding.GoalsChangeModalBinding

class TrackerScreen : Fragment(R.layout.fragment_tracker_screen) {
    private lateinit var binding: FragmentTrackerScreenBinding
    private lateinit var goalsModalBinding: GoalsChangeModalBinding
    private lateinit var appCtrl: AppCtrl
    private var modal: BottomSheetDialog? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTrackerScreenBinding.inflate(inflater, container, false)
        appCtrl = ViewModelProvider(requireActivity())[AppCtrl::class.java]

        val buttonChangeGoal = binding.trackerScreenChangeGoal
        buttonChangeGoal.setOnClickListener { onChangeGoalsClick() }
        createObservables()
        return binding.root
    }

    private fun createObservables() {
        appCtrl.selectedGoalId.observe(viewLifecycleOwner) { goalId ->
            if (goalId != null) {
                val trackerScreenDaysList = binding.trackerScreenDaysList
                val goal = appCtrl.selectedGoal!!
                val context = requireContext()
                trackerScreenDaysList.setHasFixedSize(true)
                trackerScreenDaysList.layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false)
                trackerScreenDaysList.adapter =
                        DayButtonAdapter(context, goal.days) { day ->
                            appCtrl.updatePendingDay(day)
                        }
                binding.trackerScreenTitle.visibility = View.VISIBLE
                binding.trackerScreenChangeGoal.visibility = View.VISIBLE
                binding.trackerScreenNotFound.visibility = View.GONE
                binding.trackerScreenTitle.text = goal.description
            } else {
                binding.trackerScreenDaysList.adapter = null
                binding.trackerScreenTitle.visibility = View.GONE
                binding.trackerScreenChangeGoal.visibility = View.GONE
                binding.trackerScreenNotFound.visibility = View.VISIBLE
                binding.trackerScreenTitle.text = ""
            }
        }
        appCtrl.changedDayId.observe(viewLifecycleOwner) { dayId ->
            if (dayId != null) {
                val goal = appCtrl.selectedGoal!!
                val index = goal.days.map { day -> day.id }.indexOf(dayId)
                if (index != -1) {
                    val trackerScreenDaysList = binding.trackerScreenDaysList
                    trackerScreenDaysList.adapter!!.notifyItemChanged(index, goal.days[index].state)
                }
                appCtrl.resetChangedDayId()
            }
        }
    }

    private fun createChangeGoalObservables() {
        appCtrl.goals.observe(viewLifecycleOwner) { goals ->
            val goalChangeModalGoalsList = goalsModalBinding.goalChangeModalGoalsList
            goalChangeModalGoalsList.setHasFixedSize(true)
            goalChangeModalGoalsList.layoutManager = LinearLayoutManager(requireContext())
            goalChangeModalGoalsList.adapter =
                    GoalSelectableAdapter(goals, appCtrl.selectedGoalId.value) { goal ->
                        appCtrl.selectGoal(goal)
                        modal!!.dismiss()
                    }
        }
        appCtrl.selectedGoalId.observe(viewLifecycleOwner) { goalId ->
            if (goalId != null) {
                val goalChangeModalGoalsList = goalsModalBinding.goalChangeModalGoalsList
                goalChangeModalGoalsList.adapter =
                        GoalSelectableAdapter(
                                appCtrl.goals.value!!,
                                appCtrl.selectedGoalId.value
                        ) { goal ->
                            appCtrl.selectGoal(goal)
                            modal!!.dismiss()
                        }
            }
        }
    }

    private fun onChangeGoalsClick() {
        if (modal == null) {
            goalsModalBinding = GoalsChangeModalBinding.inflate(layoutInflater, null, false)
            modal = BottomSheetDialog(requireContext(), R.style.ThemeGoalsModal)
            modal!!.setContentView(goalsModalBinding.root)
            goalsModalBinding.goalChangeModalButtonClose.setOnClickListener { modal!!.dismiss() }
            createChangeGoalObservables()
        }
        modal!!.show()
    }
}
