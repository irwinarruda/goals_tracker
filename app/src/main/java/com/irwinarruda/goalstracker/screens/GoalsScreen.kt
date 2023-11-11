package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.FragmentGoalsScreenBinding
import com.irwinarruda.goalstracker.databinding.GoalCreateModalBinding
import java.text.SimpleDateFormat
import java.util.*

class GoalsScreen : Fragment(R.layout.fragment_goals_screen) {
    private lateinit var binding: FragmentGoalsScreenBinding
    private lateinit var goalCreateModalBinding: GoalCreateModalBinding
    private var modal: BottomSheetDialog? = null
    private var datePicker: MaterialDatePicker<Long>? = null
    private var dateText = ""

    private fun formatDate(selectedDate: Long): String {
        // Create a SimpleDateFormat with the desired format
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        // Create a Date object using the selectedDate in milliseconds
        val date = Date(selectedDate)
        // Format the date and return the formatted string
        return simpleDateFormat.format(date)
    }

    private fun onHideKeyboard(view: View, hasFocus: Boolean): Unit {
        if (!hasFocus) {
            val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!;
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
        }
    }

    private fun onDatePickerOpen() {
        if (datePicker == null) {
            datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Selecione a data")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker!!.addOnPositiveButtonClickListener {
                val formattedDate = formatDate(it)
                dateText = formattedDate
                goalCreateModalBinding.goalCreateModalInputDate.editText!!.setText(formattedDate)
            }
        }
        datePicker!!.show(parentFragmentManager, "Date Picker")
    }

    private fun onCreateGoalModalOpen() {
        if (modal == null) {
            goalCreateModalBinding = GoalCreateModalBinding.inflate(layoutInflater, null, false)
            modal = BottomSheetDialog(requireContext(), R.style.ThemeGoalsModal).apply {
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
            // modal!!.
            modal!!.setContentView(goalCreateModalBinding.root)
            goalCreateModalBinding.goalCreateModalButtonClose.setOnClickListener { modal!!.dismiss() }
            goalCreateModalBinding.goalCreateModalInputDate.setEndIconOnClickListener { onDatePickerOpen() }
            goalCreateModalBinding.goalCreateModalInputDate.editText!!.doOnTextChanged { _, _, _, _ ->
                dateText = ""
            }
            goalCreateModalBinding.goalCreateModalInputDate.editText!!.setOnFocusChangeListener { v, h ->
                onHideKeyboard(v, h)
            }
            goalCreateModalBinding.goalCreateModalInputDescription.editText!!.setOnFocusChangeListener { v, h ->
                onHideKeyboard(v, h)
            }
            goalCreateModalBinding.goalCreateModalInputCoins.editText!!.setOnFocusChangeListener { v, h ->
                onHideKeyboard(v, h)
            }
            goalCreateModalBinding.goalCreateModalInputDays.editText!!.setOnFocusChangeListener { v, h ->
                onHideKeyboard(v, h)
            }

        }
        goalCreateModalBinding.goalCreateModalInputDate.editText!!.setText("DD/MM/YYYY")
        modal!!.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGoalsScreenBinding.inflate(inflater, container, false)

        binding.goalsScreenFab.setOnClickListener { onCreateGoalModalOpen() }
        return binding.root
    }
}