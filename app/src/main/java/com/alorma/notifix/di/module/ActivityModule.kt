package com.alorma.notifix.di.module

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder

open class ActivityModule(val activity: Activity) {
    fun getPermission(): DexterBuilder.Permission = Dexter.withActivity(activity)
}