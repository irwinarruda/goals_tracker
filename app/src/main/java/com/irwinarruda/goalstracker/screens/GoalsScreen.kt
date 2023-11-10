package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.FragmentGoalsScreenBinding
import com.irwinarruda.goalstracker.databinding.GoalCreateModalBinding

class GoalsScreen : Fragment(R.layout.fragment_goals_screen) {
    private lateinit var binding: FragmentGoalsScreenBinding
    private lateinit var goalCreateModalBinding: GoalCreateModalBinding
    private var modal: BottomSheetDialog? = null

    private fun onChangeGoalsClick() {
        if (modal == null) {
            goalCreateModalBinding = GoalCreateModalBinding.inflate(layoutInflater, null, false)
            modal = BottomSheetDialog(requireContext(), R.style.ThemeGoalsModal)
            modal!!.setContentView(goalCreateModalBinding.root)
            goalCreateModalBinding.goalCreateModalButtonClose.setOnClickListener { modal!!.dismiss() }
        }
        modal!!.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGoalsScreenBinding.inflate(inflater, container, false)

        val goalsScreenFab = binding.goalsScreenFab
        goalsScreenFab.setOnClickListener { onChangeGoalsClick() }
        return binding.root
    }
}