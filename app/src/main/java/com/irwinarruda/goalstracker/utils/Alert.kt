package com.irwinarruda.goalstracker.utils

import android.app.AlertDialog
import android.content.Context

class Alert {
    companion object {
        fun simple(context: Context, title: String, button: String? = null) {
            val builder = AlertDialog.Builder(context).setMessage(title)
            if (button != null) builder.setPositiveButton(button) { _, _ -> }
            builder.create().show()
        }
    }
}