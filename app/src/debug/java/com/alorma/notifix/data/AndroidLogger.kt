package com.alorma.notifix.data

import android.util.Log

class AndroidLogger : Logger {

    companion object {
        private const val TAG = "Notfix"
    }

    override fun i(msg: String) {
        Log.i(TAG, msg)
    }

    override fun d(msg: String) {
        Log.d(TAG, msg)
    }

    override fun e(msg: String, throwable: Throwable?) {
        throwable?.let { Log.e(TAG, msg, throwable) } ?: Log.e(TAG, msg)
    }
}