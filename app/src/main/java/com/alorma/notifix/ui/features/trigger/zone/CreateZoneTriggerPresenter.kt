package com.alorma.notifix.ui.features.trigger.zone

import com.alorma.notifix.data.Logger
import com.alorma.notifix.ui.commons.BasePresenter
import javax.inject.Inject

class CreateZoneTriggerPresenter @Inject constructor(
        val logger: Logger)
    : BasePresenter<CreateZoneTriggerState, CreateZoneTriggerRoute, CreateZoneTriggerAction, CreateZoneTriggerView>(logger) {

    override fun action(action: CreateZoneTriggerAction) {
        when (action) {

        }
    }
}
