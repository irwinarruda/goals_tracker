package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.components.GoalItemAdapter
import com.irwinarruda.goalstracker.databinding.FragmentGoalsScreenBinding
import com.irwinarruda.goalstracker.databinding.GoalCreateModalBinding
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.utils.Alert
import com.irwinarruda.goalstracker.utils.formatDate
import com.irwinarruda.goalstracker.utils.setKeyboardDismiss
import com.irwinarruda.goalstracker.utils.toLocalDate

class GoalsScreen : Fragment(R.layout.fragment_goals_screen) {
    private lateinit var binding: FragmentGoalsScreenBinding
    private lateinit var createGoalBinding: GoalCreateModalBinding
    private lateinit var goalsViewModel: GoalsScreenCtrl
    private var modal: BottomSheetDialog? = null
    private var datePicker: MaterialDatePicker<Long>? = null
    private var dateText = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGoalsScreenBinding.inflate(inflater, container, false)

        goalsViewModel = ViewModelProvider(requireActivity())[GoalsScreenCtrl::class.java]
        binding.goalsScreenFab.setOnClickListener { onCreateGoalModalOpen() }
        createObservables()
        goalsViewModel.list()
        return binding.root
    }

    private fun createObservables() {
        goalsViewModel.goalsList.observe(viewLifecycleOwner) {
            val goalsScreenList = binding.goalsScreenList
            goalsScreenList.setHasFixedSize(true)
            goalsScreenList.layoutManager = LinearLayoutManager(requireContext())
            goalsScreenList.adapter = GoalItemAdapter(it) { goal ->
                goalsViewModel.delete(goal.id)
            }
        }
    }

    private fun createFields() {
        createGoalBinding.goalCreateModalInputDescription.editText!!.setKeyboardDismiss(requireContext())

        createGoalBinding.goalCreateModalInputDays.editText!!.setKeyboardDismiss(requireContext())

        createGoalBinding.goalCreateModalInputDate.setEndIconOnClickListener { onDatePickerOpen() }
        createGoalBinding.goalCreateModalInputDate.editText!!.doOnTextChanged { _, _, _, _ ->
            dateText = 0
        }
        createGoalBinding.goalCreateModalInputDate.editText!!.setKeyboardDismiss(requireContext())

        createGoalBinding.goalCreateModalCheckboxUseCoins.setOnCheckedChangeListener { _, isChecked ->
            createGoalBinding.goalCreateModalInputCoins.isEnabled = isChecked
            createGoalBinding.goalCreateModalInputCoins.editText!!.setText("")
        }
        createGoalBinding.goalCreateModalInputCoins.editText!!.setKeyboardDismiss(requireContext())
    }

    private fun onSubmit() {
        val data = getFormData()
        val context = requireContext()
        if (data.descriptionText == "" || data.descriptionText.length < 3) {
            Alert.simple(context, "A descrição é obrigatória", "Ok")
            return
        }
        if (data.dayText == "" || data.dayText.toInt() < 0) {
            Alert.simple(context, "Os dias são obrigatórios e devem ser maior que 0", "Ok")
            return
        }
        if (data.dateText == 0L) {
            Alert.simple(context, "Data inválida, selecione novamente clicando no calendário", "Ok")
            return
        }
        if (data.shouldUseCoins && (data.coinsText == "" || data.coinsText.toInt() < 0)) {
            Alert.simple(context, "Digite um valor de coins válido", "Ok")
            return
        }
        goalsViewModel.create(data.toGoal())
        Alert.simple(context, "Deu certo")
    }

    private fun onDatePickerOpen() {
        if (datePicker == null) {
            datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Selecione a data")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker!!.addOnPositiveButtonClickListener {
                val formattedDate = it.formatDate("dd/MM/yyyy")
                createGoalBinding.goalCreateModalInputDate.editText!!.setText(formattedDate)
                dateText = it
            }
        }
        datePicker!!.show(parentFragmentManager, "Date Picker")
    }

    private fun onCreateGoalModalOpen() {
        if (modal == null) {
            createGoalBinding = GoalCreateModalBinding.inflate(layoutInflater, null, false)
            modal = BottomSheetDialog(requireContext(), R.style.ThemeGoalsModal)
            modal!!.setContentView(createGoalBinding.root)
            modal!!.setOnDismissListener { resetFormData() }
            createGoalBinding.goalCreateModalButtonClose.setOnClickListener { modal!!.dismiss() }
            createFields()
            createGoalBinding.goalCreateModalSubmitButton.setOnClickListener { onSubmit() }
        }
        createGoalBinding.goalCreateModalInputDate.editText!!.setText("DD/MM/YYYY")
        modal!!.show()
    }


    private fun getFormData(): FormData {
        return FormData(
            createGoalBinding.goalCreateModalInputDescription.editText!!.text.toString(),
            createGoalBinding.goalCreateModalInputDays.editText!!.text.toString(),
            dateText,
            createGoalBinding.goalCreateModalCheckboxUseCoins.isChecked,
            createGoalBinding.goalCreateModalInputCoins.editText!!.text.toString()
        )
    }

    private fun resetFormData() {
        createGoalBinding.goalCreateModalInputDescription.editText!!.setText("")
        createGoalBinding.goalCreateModalInputDays.editText!!.setText("")
        createGoalBinding.goalCreateModalInputDate.editText!!.setText("")
        dateText = 0
        createGoalBinding.goalCreateModalInputCoins.editText!!.setText("")
    }

    data class FormData(
        var descriptionText: String,
        var dayText: String,
        var dateText: Long,
        var shouldUseCoins: Boolean,
        var coinsText: String,
    ) {
        fun toGoal(): Goal {
            return Goal(
                descriptionText,
                dayText.toInt(),
                dateText.toLocalDate(),
                if (shouldUseCoins) coinsText.toInt() else null
            )
        }
    }
}