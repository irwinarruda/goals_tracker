package com.irwinarruda.goalstracker.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.R
import com.irwinarruda.goalstracker.databinding.AppBarBinding

class AppBar @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = AppBarBinding.inflate(LayoutInflater.from(context), this, true)
    private var title: String? = null;
    private var coins: Int? = null;

    init {
        setLayout(attrs)
    }

    fun setOnClickCoins(cb: (view: View) -> Unit) {
        binding.appBarCoin.setOnClickListener(cb)
    }

    fun setCoinsClickale(clickable: Boolean) {
        binding.appBarCoin.isEnabled = clickable
        if (clickable) {
            binding.appBarCoin.setBackgroundResource(R.drawable.use_coins_clickable_layer)
        } else {
            binding.appBarCoin.setBackgroundResource(R.drawable.use_coins_layer)
        }
    }

    fun setCoins(newCoins: Int) {
        binding.appBarCoinText.text = "$newCoins ${context.getString(R.string.appBar_coinsSuffix)}"
        coins = newCoins
    }

    fun setTitle(newTitle: String) {
        binding.appBarCoinText.text = newTitle
        title = newTitle
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBar)
            val attrTitle = attributes.getString(R.styleable.AppBar_appBar_title)
            setTitle(attrTitle ?: "")
            val attrCoins = attributes.getInt(R.styleable.AppBar_appBar_coins, -1)
            setCoins(
                if (attrCoins != -1) {
                    attrCoins
                } else {
                    0
                }
            )
            attributes.recycle()
        }
    }
}