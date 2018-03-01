package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.data.Logger
import com.alorma.notifix.data.framework.AndroidGetContact
import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.commons.BasePresenter
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

class CreateTriggerPresenter @Inject constructor(
        private val permissionRequest: DexterBuilder.SinglePermissionListener,
        private val androidGetContact: AndroidGetContact,
        val logger: Logger)
    : BasePresenter<CreateTriggerState, CreateTriggerRoute, CreateTriggerAction, CreateTriggerView>(logger) {

    override fun action(action: CreateTriggerAction) {
        when (action) {
            is RequestContactAction -> onContactRequest()
            is ContactImportAction -> onContactImport(action)
        }
    }

    private fun onContactRequest() {
        permissionRequest.withListener(object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                navigate(SelectContact())
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                render(if (response.isPermanentlyDenied) {
                    DeniedAlwaysPermissionMessage()
                } else {
                    DeniedPermissionMessage()
                })
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                token.continuePermissionRequest()
            }
        }).check()
    }

    private fun onContactImport(action: ContactImportAction) {
        disposables += androidGetContact.loadContact(action.uri)
                .subscribeOnIO()
                .observeOnUI()
                .subscribe({
                    render(ContactLoaded(it))
                }, {
                    logger.e("Contact error: $it", it)
                })
    }
}
