package com.alorma.notifix.ui.features.trigger.number

import android.net.Uri
import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class CreateTriggerAction : Action() {
    class RequestContactAction : CreateTriggerAction()
    class ContactImportAction(val uri: Uri) : CreateTriggerAction()
}

sealed class CreateTriggerRoute : Route() {
    class SelectContact : CreateTriggerRoute()
}

sealed class CreateTriggerState : State() {
    class DeniedPermissionMessage : CreateTriggerState()
    class DeniedAlwaysPermissionMessage : CreateTriggerState()
    class ContactLoaded(val contact: Contact) : CreateTriggerState()
}

interface CreateTriggerView : BaseView<CreateTriggerState, CreateTriggerRoute>