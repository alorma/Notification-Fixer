package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.data.Logger
import com.alorma.notifix.ui.commons.BasePresenter
import javax.inject.Inject

class CreateTriggerPresenter @Inject constructor(val logger: Logger)
    : BasePresenter<CreateTriggerState, CreateTriggerRoute, CreateTriggerAction, CreateTriggerView>(logger) {

    override fun action(action: CreateTriggerAction) {
        when (action) {
            is ContactPermissionAction.RequestContactAction -> onContactRequest()
            is ContactPermissionAction.Approved -> onContactPermissionApproved()
            is ContactPermissionAction.Denied -> onContactPermissionDenied(action.permanent)
        }
    }

    private fun onContactRequest() {
        render(RequestContactPermission())
    }

    private fun onContactPermissionApproved() {
        navigate(SelectContact())
    }

    private fun onContactPermissionDenied(permanent: Boolean) {
        render(if (permanent) {
            DeniedAlwaysPermissionMessage()
        } else {
            DeniedPermissionMessage()
        })
    }
}
