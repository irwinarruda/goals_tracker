package com.irwinarruda.goalstracker.utils

import android.content.res.Resources

class Sizes {
    companion object {
        fun dpToPixels(dp: Number): Int {
            val density = Resources.getSystem().displayMetrics.density
            return (dp.toFloat() * density).toInt()
        }
    }
}