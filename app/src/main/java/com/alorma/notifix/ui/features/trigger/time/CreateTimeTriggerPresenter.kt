package com.alorma.notifix.ui.features.trigger.time

import com.alorma.notifix.data.Logger
import com.alorma.notifix.ui.commons.BasePresenter
import javax.inject.Inject

class CreateTimeTriggerPresenter @Inject constructor(
        val logger: Logger)
    : BasePresenter<CreateTimeTriggerState, CreateTimeTriggerRoute, CreateTimeTriggerAction, CreateTimeTriggerView>(logger) {

    override fun action(action: CreateTimeTriggerAction) {
        when (action) {
            is CreateTimeTriggerAction.TimeSelectedAction -> onTimeSelected(action)
        }
    }

    private fun onTimeSelected(action: CreateTimeTriggerAction.TimeSelectedAction) {

    }
}
