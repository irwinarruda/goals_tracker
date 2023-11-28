package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.components.TabAdapter
import com.irwinarruda.goalstracker.controllers.AppCtrl
import com.irwinarruda.goalstracker.databinding.ActivityMainBinding
import com.irwinarruda.goalstracker.databinding.CoinsDialogBinding
import com.irwinarruda.goalstracker.entities.DayState
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.utils.Alert
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var coinsDialogBinding: CoinsDialogBinding
    private lateinit var appCtrl: AppCtrl
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tabPanel.adapter = TabAdapter(this)
        TabAdapter.createLayoutMediator(this, binding.tabs!!, binding.tabPanel!!)
        binding.appbar.setOnClickCoins { onClickCoins() }
        appCtrl = ViewModelProvider(this)[AppCtrl::class.java]
        appCtrl.start()
        createObservables()
    }

    fun updateCoinsButton(goal: Goal?, userCoins: Int) {
        binding.appbar.setCoins(userCoins)
        if (goal == null) {
            binding.appbar.setCoinsClickale(false)
            return
        }
        val day = goal.days.find { day -> day.date.isEqual(LocalDate.now()) }
        if (day != null && day.state == DayState.PENDING) {
            binding.appbar.setCoinsClickale(goal.coins != null && goal.coins!! <= userCoins)
        } else {
            binding.appbar.setCoinsClickale(false)
        }
    }

    fun updateCoinsModal(goal: Goal?, userCoins: Int) {
        if (goal?.coins == null) return
        coinsDialogBinding.dialogCoinsValue.text = "${userCoins} ${this.getString(R.string.coinsDialog_coinsSuffix)}"
        coinsDialogBinding.dialogDescription.text =
            this.getString(R.string.coinsDialog_description).replace("{1}", goal.coins.toString())
                .replace("{2}", (userCoins - goal.coins!!).toString())
        coinsDialogBinding.dialogButtonConfirm.setOnClickListener {
            val day = goal.days.find { day -> day.date.isEqual(LocalDate.now()) }
            if (day != null && day.state == DayState.PENDING) {
                appCtrl.updateGoldDay(day)
            } else {
                Alert.simple(this, this.getString(R.string.coinsDialog_confirmError), "Ok")
            }
            dialog!!.dismiss()
        }
    }

    fun createObservables() {
        appCtrl.userCoins.observe(this) { userCoins ->
            updateCoinsButton(appCtrl.selectedGoal, userCoins)
        }
        appCtrl.selectedGoalId.observe(this) { _ ->
            updateCoinsButton(appCtrl.selectedGoal, appCtrl.userCoins.value!!)
        }
    }

    fun createCoinsObservable() {
        appCtrl.userCoins.observe(this) { userCoins ->
            updateCoinsModal(appCtrl.selectedGoal, userCoins)
        }
        appCtrl.selectedGoalId.observe(this) { _ ->
            updateCoinsModal(appCtrl.selectedGoal, appCtrl.userCoins.value!!)
        }
    }

    private fun onClickCoins() {
        if (dialog == null) {
            val build = AlertDialog.Builder(this, R.style.ThemeCoinsDialog)
            coinsDialogBinding = CoinsDialogBinding.inflate(layoutInflater, null, false)
            build.setView(coinsDialogBinding.root)
            dialog = build.create()
            coinsDialogBinding.dialogButtonClose.setOnClickListener { dialog!!.dismiss() }
            createCoinsObservable()
        }
        dialog!!.show()
    }
}