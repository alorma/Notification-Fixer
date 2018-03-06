package com.alorma.notifix.ui.features.trigger.number

import android.net.Uri
import com.alorma.notifix.data.Logger
import com.alorma.notifix.data.framework.AndroidGetContact
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.usecase.CreateTriggerUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.features.trigger.TriggerRoute
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
        private val createTriggerUseCase: CreateTriggerUseCase,
        @Named(CreateTriggerModule.PERMISSION_READ_CONTACTS)
        private val permissionRequest: DexterBuilder.SinglePermissionListener,
        private val androidGetContact: AndroidGetContact,
        val logger: Logger)
    : BasePresenter<CreateNumberTriggerState, Route, CreateNumberTriggerAction, CreateNumberTriggerView>(logger) {

    private lateinit var selectedUri: Uri

    override fun action(action: CreateNumberTriggerAction) {
        when (action) {
            is CreateNumberTriggerAction.RequestContactAction -> onContactRequest()
            is CreateNumberTriggerAction.ContactImportAction -> onContactImport(action)
            is CreateNumberTriggerAction.SelectContactAction -> onSelectContact(action)
        }
    }

    private fun onContactRequest() {
        permissionRequest.withListener(object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                navigate(CreateNumberTriggerRoute.SelectContactRoute())
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

    private fun onContactImport(action: CreateNumberTriggerAction.ContactImportAction) {
        this.selectedUri = action.uri
        disposables += androidGetContact.loadContact(action.uri)
                .subscribeOnIO()
                .observeOnUI()
                .subscribe({
                    render(CreateNumberTriggerState.ContactLoaded(it))
                }, {
                    logger.e("Contact error: $it", it)
                })
    }

    private fun onSelectContact(action: CreateNumberTriggerAction.SelectContactAction) {
        val payload: NotificationTriggerPayload.NumberPayload = when(action.type) {
            is Type.PHONE -> NotificationTriggerPayload.NumberPayload.PhonePayload(selectedUri.toString())
            is Type.SMS -> NotificationTriggerPayload.NumberPayload.SmsPayload(selectedUri.toString())
        }
        disposables += createTriggerUseCase.execute(payload)
                .observeOnUI()
                .subscribe({
                    navigate(TriggerRoute.Success(it))
                }, {})
    }
}
