package com.irwinarruda.goalstracker.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat

class Keyboard {
    companion object {
        fun onHideKeyboard(context: Context, view: View, hasFocus: Boolean): Unit {
            if (!hasFocus) {
                val inputMethodManager = ContextCompat.getSystemService(context, InputMethodManager::class.java)!!;
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
            }
        }
    }
}

fun EditText.setKeyboardDismiss(context: Context) {
    this.setOnFocusChangeListener { v, h ->
        Keyboard.onHideKeyboard(context, v, h)
    }
}
