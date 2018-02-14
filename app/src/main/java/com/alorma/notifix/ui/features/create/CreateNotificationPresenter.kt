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

    override fun onAction(action: CreateNotificationAction) {
        when (action) {
            is NewNotificationAction -> onNewAction(action)
            is PreviewNotificationAction -> onPreviewAction(action)
        }
    }

    private fun onNewAction(action: NewNotificationAction) {
        disposables += insertNotificationUseCase.execute(actionMapper.mapCreate(action))
                .subscribeOnIO()
                .subscribe({
                    navigate(routeMapper.mapComplete())
                }, {

                })
    }

    private fun onPreviewAction(action: PreviewNotificationAction) {
        disposables += showNotificationUseCase.showPreview(actionMapper.mapPreview(action))
                .observeOnUI()
                .doOnSubscribe {
                    dismissPreview()
                }
                .subscribe({}, {})
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