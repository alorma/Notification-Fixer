package com.alorma.notifix.ui.features.trigger.di

import android.Manifest
import android.app.Activity
import com.alorma.notifix.di.module.ActivityModule
import com.karumi.dexter.DexterBuilder
import dagger.Module
import dagger.Provides

@Module
class CreateTriggerModule(activity: Activity) : ActivityModule(activity) {

    @Provides
    fun getContactPermission(): DexterBuilder.SinglePermissionListener
            = getPermission().withPermission(Manifest.permission.READ_CONTACTS)

}