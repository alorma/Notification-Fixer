package com.alorma.notifix.ui.features.trigger.number

import android.net.Uri
import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class CreateNumberTriggerAction : Action() {
    class RequestContactActionNumber : CreateNumberTriggerAction()
    class ContactImportActionNumber(val uri: Uri) : CreateNumberTriggerAction()
}

sealed class CreateNumberTriggerRoute : Route() {
    class SelectContact : CreateNumberTriggerRoute()
}

sealed class CreateNumberTriggerState : State() {
    class DeniedPermissionMessage : CreateNumberTriggerState()
    class DeniedAlwaysPermissionMessage : CreateNumberTriggerState()
    class ContactLoaded(val contact: Contact) : CreateNumberTriggerState()
}

interface CreateNumberTriggerView : BaseView<CreateNumberTriggerState, CreateNumberTriggerRoute>