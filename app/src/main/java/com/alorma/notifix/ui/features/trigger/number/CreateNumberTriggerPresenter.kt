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
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import javax.inject.Inject
import javax.inject.Named

class CreateNumberTriggerPresenter @Inject constructor(
        private val createTriggerUseCase: CreateTriggerUseCase,
        @Named(CreateTriggerModule.PERMISSION_READ_CONTACTS)
        private val permissionRequest: DexterBuilder.MultiPermissionListener,
        private val androidGetContact: AndroidGetContact,
        logger: Logger)
    : BasePresenter<CreateNumberTriggerState, Route, CreateNumberTriggerAction, CreateNumberTriggerView>(logger) {

    private lateinit var selectedUri: Uri
    private lateinit var contactPhone: String

    override fun action(action: CreateNumberTriggerAction) {
        when (action) {
            is CreateNumberTriggerAction.RequestContactAction -> onContactRequest()
            is CreateNumberTriggerAction.ContactImportAction -> onContactImport(action)
            is CreateNumberTriggerAction.SelectContactAction -> onSelectContact(action)
        }
    }

    private fun onContactRequest() {
        permissionRequest.withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    navigate(CreateNumberTriggerRoute.SelectContactRoute())
                } else {
                    render(if (report.isAnyPermissionPermanentlyDenied) {
                        CreateNumberTriggerState.DeniedAlwaysPermissionMessage()
                    } else {
                        CreateNumberTriggerState.DeniedPermissionMessage()
                    })
                }
            }

            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>,
                                                            token: PermissionToken) {
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
                    if (it.phone != null) {
                        this@CreateNumberTriggerPresenter.contactPhone = it.phone
                        render(CreateNumberTriggerState.ContactLoaded(it))
                    }
                }, {
                    logger.e("Contact error: $it", it)
                })
    }

    private fun onSelectContact(action: CreateNumberTriggerAction.SelectContactAction) {
        val payload: NotificationTriggerPayload.NumberPayload = when (action.type) {
            is Type.PHONE -> NotificationTriggerPayload.NumberPayload.PhonePayload(selectedUri.toString(), contactPhone)
            is Type.SMS -> NotificationTriggerPayload.NumberPayload.SmsPayload(selectedUri.toString(), contactPhone)
        }
        disposables += createTriggerUseCase.execute(payload)
                .observeOnUI()
                .subscribe({
                    navigate(TriggerRoute.Success(it))
                }, {})
    }
}
