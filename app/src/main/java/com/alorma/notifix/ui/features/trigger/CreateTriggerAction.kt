package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.ui.commons.Action

sealed class CreateTriggerAction : Action()

sealed class ContactPermissionAction : CreateTriggerAction() {
    class RequestContactAction : ContactPermissionAction()
    class Approved() : ContactPermissionAction()
    class Denied(val permanent: Boolean) : ContactPermissionAction()
}