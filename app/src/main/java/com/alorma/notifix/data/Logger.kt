package com.alorma.notifix.data

interface Logger {
    infix fun i(msg: String)
    infix fun d(msg: String)
    fun e(msg: String, throwable: Throwable? = null)
}