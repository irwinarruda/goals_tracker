package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.ActivityMainBinding
import com.irwinarruda.goalstracker.databinding.CoinsDialogBinding
import com.irwinarruda.goalstracker.utils.TabAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var dialog: AlertDialog
    private lateinit var binding: ActivityMainBinding

    private fun onClickCoins() {
        val build = AlertDialog.Builder(this, R.style.ThemeCoinsDialog)
        val view = CoinsDialogBinding.inflate(layoutInflater, null, false)
        build.setView(view.root)
        view.dialogButtonClose.setOnClickListener { dialog.dismiss() }
        dialog = build.create()
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tabPanel!!.adapter = TabAdapter(this)
        TabAdapter.createLayoutMediator(binding.tabs!!, binding.tabPanel!!)
        binding.appbar!!.setOnClickCoins { onClickCoins() }
    }
}