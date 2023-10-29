package com.irwinarruda.goalstracker.utils

import android.content.Context
import android.widget.Toast

class Log {
    companion object {
        fun show(context: Context, vararg arg: String) {
            Toast.makeText(context, arg.joinToString(","), Toast.LENGTH_SHORT).show()
        }

        fun showLong(context: Context, vararg arg: String) {
            Toast.makeText(context, arg.joinToString(","), Toast.LENGTH_LONG).show()
        }
    }
}