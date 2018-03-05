package com.alorma.notifix.ui.features.trigger.zone

import com.alorma.notifix.data.Logger
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import javax.inject.Inject
import javax.inject.Named

class CreateZoneTriggerPresenter @Inject constructor(
        @Named(CreateTriggerModule.PERMISSION_LOCATION)
        private val permissionRequest: DexterBuilder.SinglePermissionListener,
        val logger: Logger)
    : BasePresenter<CreateZoneTriggerState, CreateZoneTriggerRoute, CreateZoneTriggerAction, CreateZoneTriggerView>(logger) {

    override fun action(action: CreateZoneTriggerAction) {
        when (action) {
            is CreateZoneTriggerAction.OnMapReadyAction -> onMapReady()
            is CreateZoneTriggerAction.SelectedLocation -> onLocationReady(action)
        }
    }

    private fun onMapReady() {
        permissionRequest.withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                render(CreateZoneTriggerState.Location.Allowed())
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                render(if (response.isPermanentlyDenied) {
                    CreateZoneTriggerState.Location.Denied()
                } else {
                    CreateZoneTriggerState.Location.DeniedAlways()
                })
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                token.continuePermissionRequest()
            }
        }).check()
    }

    private fun onLocationReady(action: CreateZoneTriggerAction.SelectedLocation) {

    }
}