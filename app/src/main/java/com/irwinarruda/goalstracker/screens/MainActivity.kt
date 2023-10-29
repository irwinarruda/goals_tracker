package com.irwinarruda.goalstracker.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.irwinarruda.goalstracker.databinding.ActivityMainBinding
import com.irwinarruda.goalstracker.utils.TabAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tabPanel!!.adapter = TabAdapter(this)
        TabAdapter.createLayoutMediator(binding.tabs!!, binding.tabPanel!!)
    }
}