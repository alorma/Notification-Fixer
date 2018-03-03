package com.alorma.notifix.ui.features.trigger.number

import com.alorma.notifix.data.Logger
import com.alorma.notifix.data.framework.AndroidGetContact
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import com.alorma.notifix.ui.utils.subscribeOnIO
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener
import javax.inject.Inject
import javax.inject.Named

class CreateNumberTriggerPresenter @Inject constructor(
        @Named(CreateTriggerModule.PERMISSION_READ_CONTACTS)
        private val permissionRequest: DexterBuilder.SinglePermissionListener,
        private val androidGetContact: AndroidGetContact,
        val logger: Logger)
    : BasePresenter<CreateNumberTriggerState, CreateNumberTriggerRoute, CreateNumberTriggerAction, CreateNumberTriggerView>(logger) {

    override fun action(action: CreateNumberTriggerAction) {
        when (action) {
            is CreateNumberTriggerAction.RequestContactActionNumber -> onContactRequest()
            is CreateNumberTriggerAction.ContactImportActionNumber -> onContactImport(action)
        }
    }

    private fun onContactRequest() {
        permissionRequest.withListener(object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                navigate(CreateNumberTriggerRoute.SelectContact())
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                render(if (response.isPermanentlyDenied) {
                    CreateNumberTriggerState.DeniedAlwaysPermissionMessage()
                } else {
                    CreateNumberTriggerState.DeniedPermissionMessage()
                })
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                token.continuePermissionRequest()
            }
        }).check()
    }

    private fun onContactImport(actionNumber: CreateNumberTriggerAction.ContactImportActionNumber) {
        disposables += androidGetContact.loadContact(actionNumber.uri)
                .subscribeOnIO()
                .observeOnUI()
                .subscribe({
                    render(CreateNumberTriggerState.ContactLoaded(it))
                }, {
                    logger.e("Contact error: $it", it)
                })
    }
}
