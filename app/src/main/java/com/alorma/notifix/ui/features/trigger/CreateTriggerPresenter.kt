package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.data.Logger
import com.alorma.notifix.ui.commons.BasePresenter
import javax.inject.Inject

class CreateTriggerPresenter @Inject constructor(private val logger: Logger) :
        BasePresenter<CreateTriggerState, CreateTriggerRoute, CreateTriggerAction, CreateTriggerView>(logger) {

    override fun action(action: CreateTriggerAction) {
        when (action) {
            is Trigger -> onTriggerChange(action)
        }
    }

    private fun onTriggerChange(trigger: Trigger) {
        when (trigger) {
            is SMS -> logger.i("SMS")
            is PHONE -> logger.i("PHONE")
            is TIME -> logger.i("TIME")
            is ZONE -> logger.i("ZONE")
        }
    }
}