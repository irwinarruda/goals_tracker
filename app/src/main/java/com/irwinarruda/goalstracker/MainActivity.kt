package com.irwinarruda.goalstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.irwinarruda.goalstracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appbar!!.onCoins {
            println("Clicked on coins")
        }
    }
}