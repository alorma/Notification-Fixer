package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.usecase.ObtainNotificationsUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.features.create.NotificationsAction
import com.alorma.notifix.ui.features.create.OnCreateSucces
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(
        private val obtainNotificationsUseCase: ObtainNotificationsUseCase,
        private val stateMapper: NotificationsStateMapper,
        private val routeMapper: NotificationsRouteMapper,
        logger: Logger)
    : BasePresenter<NotificationsState, NotificationsRoute,
        NotificationsAction, NotificationsView>(logger) {

    override fun onAction(action: NotificationsAction) = when (action) {
        is OnCreateSucces -> loadNotifications()
        else -> {
        }
    }

    override fun onCreate() {
        loadNotifications()
    }

    private fun loadNotifications() {
        disposables += obtainNotificationsUseCase.execute()
                .observeOnUI()
                .subscribe({
                    render(stateMapper.mapSuccess(it))
                }, {}, {})
    }

    fun onAddNotification() {
        navigate(routeMapper.mapCreate())
    }
}