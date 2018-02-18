package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.model.Trigger
import com.alorma.notifix.domain.usecase.ObtainTriggersUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import javax.inject.Inject

class CreateTriggerPresenter @Inject constructor(
        private val logger: Logger,
        private val obtainTriggersUseCase: ObtainTriggersUseCase,
        private val createTriggerMapper: CreateTriggerMapper) :
        BasePresenter<CreateTriggerState, CreateTriggerRoute, CreateTriggerAction, CreateTriggerView>(logger) {

    override fun action(action: CreateTriggerAction) {
        when (action) {
            is OnTriggerSelected -> onTriggerChange(action.trigger)
        }
    }

    override fun onCreate() {
        super.onCreate()

        disposables += obtainTriggersUseCase.execute()
                .observeOnUI()
                .subscribe({
                    render(createTriggerMapper.mapTriggers(it))
                }, {})
    }

    private fun onTriggerChange(trigger: Trigger) {

    }
}