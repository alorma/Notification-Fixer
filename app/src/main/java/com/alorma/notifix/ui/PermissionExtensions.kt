package com.alorma.notifix.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener


fun Context.grantContactsPermission(onPermissionsGranted: () -> Unit,
                                    onPermissionDenied: (Boolean) -> Unit) {
    grantPermission(Manifest.permission.READ_CONTACTS, onPermissionsGranted, onPermissionDenied)
}

fun Context.grantPermission(permission: String,
                            onPermissionsGranted: () -> Unit,
                            onPermissionDenied: (Boolean) -> Unit) {
    Dexter.withActivity(this as Activity)
            .withPermission(permission)
            .withListener(object : BasePermissionListener() {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    onPermissionsGranted()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    onPermissionDenied(response.isPermanentlyDenied)
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
}