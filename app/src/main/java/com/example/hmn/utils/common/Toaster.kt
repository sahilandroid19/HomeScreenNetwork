package com.example.hmn.utils.common

import android.content.Context
import android.widget.Toast

object Toaster {

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}