package com.irwinarruda.goalstracker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.irwinarruda.goalstracker.databinding.AppBarBinding

class AppBar @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = AppBarBinding.inflate(LayoutInflater.from(context), this, true)
    private var title: String? = null;
    private var coins: Int? = null;

    init {
        setLayout(attrs)
    }

    fun onCoins(cb: (view: View) -> Unit) {
        binding.appbarCoin.setOnClickListener(cb)
    }

    fun setCoins(coins: Int) {
        binding.appbarCoinText.text = "$coins ${context.getString(R.string.coin_prefix)}"
        this.coins = coins
    }

    fun setTitle(title: String) {
        binding.appbarCoinText.text = title
        this.title = title
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBar)
            val attrTitle = attributes.getString(R.styleable.AppBar_title);
            setTitle(attrTitle ?: "")
            val attrCoins = attributes.getInt(R.styleable.AppBar_coins, -1);
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