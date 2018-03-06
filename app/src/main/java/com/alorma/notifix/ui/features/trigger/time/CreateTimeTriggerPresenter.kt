package com.alorma.notifix.ui.features.trigger.time

import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.usecase.CreateTriggerUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.features.trigger.TriggerRoute
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import javax.inject.Inject

class CreateTimeTriggerPresenter @Inject constructor(
        private val createTriggerUseCase: CreateTriggerUseCase,
        val logger: Logger)
    : BasePresenter<CreateTimeTriggerState, TriggerRoute, CreateTimeTriggerAction, CreateTimeTriggerView>(logger) {

    override fun action(action: CreateTimeTriggerAction) {
        when (action) {
            is CreateTimeTriggerAction.TimeSelectedAction -> onTimeSelected(action)
        }
    }

    private fun onTimeSelected(action: CreateTimeTriggerAction.TimeSelectedAction) {
        val payload = NotificationTriggerPayload.TimePayload(action.hour, action.minute)
        disposables += createTriggerUseCase.execute(payload)
                .observeOnUI()
                .subscribe({
                    navigate(TriggerRoute.Success(it))
                }, {})
    }
}
