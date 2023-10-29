package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.components.IconButton
import com.irwinarruda.goalstracker.utils.Log

class TrackerScreen : Fragment(R.layout.fragment_tracker_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonChangeGoal = view.findViewById<IconButton>(R.id.button_changeGoal)
        buttonChangeGoal.setOnClickListener {
            Log.show(requireContext(), "I'm here")
        }
    }
}