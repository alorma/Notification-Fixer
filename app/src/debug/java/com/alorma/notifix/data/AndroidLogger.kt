package com.alorma.notifix.data

import android.util.Log

class AndroidLogger : Logger{

    companion object {
        private const val TAG = "Notfix"
    }

    override fun i(msg: String) {
        Log.i(TAG, msg)
    }

    override fun d(msg: String) {
        Log.d(TAG, msg)
    }
}