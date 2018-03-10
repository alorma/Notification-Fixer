package com.alorma.notifix.ui.features.trigger.preview

import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.usecase.GetTriggerUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import javax.inject.Inject

class TriggerPreviewPresenter @Inject constructor(
        private val triggerUseCase: GetTriggerUseCase,
        private val triggerMapper: TriggerMapper,
        logger: Logger)
    : BasePresenter<TriggerPreviewState, TriggerPreviewRoute, TriggerPreviewAction, TriggerPreviewView>(logger) {

    override fun action(action: TriggerPreviewAction) {
        when (action) {
            is TriggerPreviewAction.LoadTrigger -> onLoadTrigger(action.id)
        }
    }

    private fun onLoadTrigger(id: Long) {
        disposables += triggerUseCase.execute(id)
                .observeOnUI()
                .subscribe({
                   render(triggerMapper.map(it))
                }, {
                    // TODO
                })
    }
}