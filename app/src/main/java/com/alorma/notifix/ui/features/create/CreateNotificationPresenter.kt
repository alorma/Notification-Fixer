package com.alorma.notifix.ui.features.create

import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.usecase.DismissNotificationUseCase
import com.alorma.notifix.domain.usecase.InsertNotificationUseCase
import com.alorma.notifix.domain.usecase.ShowNotificationUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import com.alorma.notifix.ui.utils.subscribeOnIO
import javax.inject.Inject

class CreateNotificationPresenter @Inject constructor(
        private val insertNotificationUseCase: InsertNotificationUseCase,
        private val showNotificationUseCase: ShowNotificationUseCase,
        private val dismissNotificationUseCase: DismissNotificationUseCase,
        private val actionMapper: CreateNotificationActionMapper,
        private val routeMapper: CreateNotificationRouteMapper,
        logger: Logger)
    : BasePresenter<CreateNotificationState, CreateNotificationRoute,
        CreateNotificationAction, CreateNotificationView>(logger) {

    private var triggerId: Long? = null

    override fun action(action: CreateNotificationAction) {
        when (action) {
            is OnTriggerSelected -> navigate(routeMapper.mapTrigger(action.type))
            is NewNotificationAction -> onNewAction(action)
            is PreviewNotificationAction -> onPreviewAction(action)
            is TriggerCreatedAction -> onTriggerCreated(action)
        }
    }

    private fun onNewAction(action: NewNotificationAction) {
        val appNotification = actionMapper.mapCreate(action, triggerId)
        disposables += insertNotificationUseCase.execute(appNotification)
                .subscribeOnIO()
                .subscribe({
                    navigate(routeMapper.mapComplete())
                }, {

                })
    }

    private fun onPreviewAction(action: PreviewNotificationAction) {
        disposables += showNotificationUseCase.showPreview(actionMapper.mapPreview(action))
                .observeOnUI()
                .subscribe({}, {})
    }

    private fun onTriggerCreated(action: TriggerCreatedAction) {
        triggerId = action.triggerId
        render(CreateNotificationState.Trigger.Any(action.triggerId))
    }

    override fun onStop() {
        dismissPreview()
        super.onStop()
    }

    private fun dismissPreview() {
        disposables += dismissNotificationUseCase.execute(actionMapper.mapPreviewNotificationId())
                .observeOnUI()
                .subscribe({}, {})
    }
}
