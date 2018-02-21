package com.alorma.notifix.ui.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}