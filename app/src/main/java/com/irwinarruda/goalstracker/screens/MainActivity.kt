package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.ActivityMainBinding
import com.irwinarruda.goalstracker.databinding.CoinsDialogBinding
import com.irwinarruda.goalstracker.utils.TabAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var coinsDialogBinding: CoinsDialogBinding
    private var dialog: AlertDialog? = null

    private lateinit var goalsViewModel: GoalsScreenCtrl

    private fun onClickCoins() {
        if (dialog == null) {
            val build = AlertDialog.Builder(this, R.style.ThemeCoinsDialog)
            coinsDialogBinding = CoinsDialogBinding.inflate(layoutInflater, null, false)
            build.setView(coinsDialogBinding.root)
            dialog = build.create()
            coinsDialogBinding.dialogButtonClose.setOnClickListener { dialog!!.dismiss() }
        }
        dialog!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tabPanel!!.adapter = TabAdapter(this)
        TabAdapter.createLayoutMediator(binding.tabs!!, binding.tabPanel!!)
        binding.appbar!!.setOnClickCoins { onClickCoins() }
        goalsViewModel = ViewModelProvider(this)[GoalsScreenCtrl::class.java]
        goalsViewModel.updateDays(1)
    }
}