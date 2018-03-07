package com.alorma.notifix.ui.features.trigger.di

import android.Manifest
import android.app.Activity
import com.alorma.notifix.di.module.ActivityModule
import com.karumi.dexter.DexterBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CreateTriggerModule(activity: Activity) : ActivityModule(activity) {

    companion object {
        const val PERMISSION_READ_CONTACTS = "READ_CONTACTS"
        const val PERMISSION_LOCATION = "LOCATION"
    }

    @Provides
    @Named(PERMISSION_READ_CONTACTS)
    fun getContactPermission(): DexterBuilder.MultiPermissionListener
            = getPermission().withPermissions(Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS)

    @Provides
    @Named(PERMISSION_LOCATION)
    fun getLocationPermission(): DexterBuilder.SinglePermissionListener
            = getPermission().withPermission(Manifest.permission.ACCESS_FINE_LOCATION)

}