package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.FragmentTrackerScreenBinding
import com.irwinarruda.goalstracker.databinding.GoalsModalBinding

class TrackerScreen : Fragment(R.layout.fragment_tracker_screen) {
    private lateinit var binding: FragmentTrackerScreenBinding
    private lateinit var goalsModalBinding: GoalsModalBinding
    private var modal: BottomSheetDialog? = null

    private fun onChangeGoalsClick() {
        if (modal == null) {
            goalsModalBinding = GoalsModalBinding.inflate(layoutInflater, null, false)
            modal = BottomSheetDialog(requireContext(), R.style.ThemeGoalsModal)
            modal!!.setContentView(goalsModalBinding.root)
            goalsModalBinding.modalButtonClose.setOnClickListener { modal!!.dismiss() }
        }
        modal!!.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTrackerScreenBinding.inflate(inflater, container, false)

        val buttonChangeGoal = binding.buttonChangeGoal
        buttonChangeGoal.setOnClickListener { onChangeGoalsClick() }
        return binding.root
    }
}