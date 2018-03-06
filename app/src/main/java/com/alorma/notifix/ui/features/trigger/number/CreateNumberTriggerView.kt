package com.alorma.notifix.ui.features.trigger.number

import android.net.Uri
import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class Type {
    class PHONE : Type()
    class SMS : Type()
}

sealed class CreateNumberTriggerAction : Action() {
    class RequestContactAction : CreateNumberTriggerAction()
    class ContactImportAction(val uri: Uri) : CreateNumberTriggerAction()
    class SelectContactAction(val type: Type) : CreateNumberTriggerAction()
}

sealed class CreateNumberTriggerRoute : Route() {
    class SelectContactRoute : CreateNumberTriggerRoute()
}

sealed class CreateNumberTriggerState : State() {
    class DeniedPermissionMessage : CreateNumberTriggerState()
    class DeniedAlwaysPermissionMessage : CreateNumberTriggerState()
    class ContactLoaded(val contact: Contact) : CreateNumberTriggerState()
}

interface CreateNumberTriggerView : BaseView<CreateNumberTriggerState, Route>