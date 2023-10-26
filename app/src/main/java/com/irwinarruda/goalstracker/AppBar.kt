package com.irwinarruda.goalstracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.databinding.AppBarBinding

class AppBar @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = AppBarBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var title: String;

    init {
        setLayout(attrs)
        binding.appbarCoin.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    fun setCoins(coins: Int) {
        binding.appbarCoinText.text = "${coins} coins"
    }

    fun setTitle(title: String) {
        binding.appbarCoinText.text = title
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBar)
            val attrTitle = attributes.getString(R.styleable.AppBar_title);
            if (attrTitle != null) {
                setTitle(attrTitle)
            }
            val attrCoins = attributes.getInt(R.styleable.AppBar_coins, -1);
            if (attrCoins != -1) {
                setCoins(attrCoins)
            }
            attributes.recycle()
        }
    }
}