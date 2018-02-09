package com.alorma.notifix.ui.features.create

import com.alorma.notifix.domain.usecase.InsertNotificationUseCase
import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.commons.OnStop
import com.alorma.notifix.ui.utils.plusAssign
import com.alorma.notifix.ui.utils.subscribeOnIO
import javax.inject.Inject

class CreateNotificationPresenter @Inject constructor(
        private val insertNotificationUseCase: InsertNotificationUseCase,
        private val actionMapper: CreateNotificationActionMapper,
        private val routeMapper: CreateNotificationRouteMapper)
    : BasePresenter<CreateNotificationState, CreateNotificationRoute, CreateNotificationView>() {

    override fun onAction(action: Action) {
        when (action) {
            is NewNotificationAction -> onNewAction(action)
            is OnStop -> destroy()
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

}