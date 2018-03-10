package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.usecase.ObtainNotificationsUseCase
import com.alorma.notifix.domain.usecase.ShowNotificationsUseCase
import com.alorma.notifix.domain.usecase.UpdateNotificationUseCase
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.features.create.NotificationsAction
import com.alorma.notifix.ui.features.create.OnCreateSucces
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(
        private val obtainNotificationsUseCase: ObtainNotificationsUseCase,
        private val showNotificationsUseCase: ShowNotificationsUseCase,
        private val updateNotificationUseCase: UpdateNotificationUseCase,
        private val stateMapper: NotificationsStateMapper,
        private val routeMapper: NotificationsRouteMapper,
        logger: Logger)
    : BasePresenter<NotificationsState, NotificationsRoute,
        NotificationsAction, NotificationsView>(logger) {

    override fun action(action: NotificationsAction) = when (action) {
        is OnCreateSucces -> {
            loadNotifications()
        }
    }

    override fun onCreate() {
        loadNotifications()
        showNotifications()
    }

    override fun onStart() {
        loadNotifications()
    }

    private fun loadNotifications() {
        disposables += obtainNotificationsUseCase.execute()
                .observeOnUI()
                .subscribe({
                    render(stateMapper.mapSuccessState(it))
                }, {}, {})
    }

    private fun showNotifications() {
        disposables += showNotificationsUseCase.execute()
                .observeOnUI()
                .subscribe({
                    logger.i("Show completed")
                }, {
                    logger.e("Show error", it)
                })
    }

    fun onAddNotification() {
        navigate(routeMapper.mapCreate())
    }

    fun updateNotification(vm: NotificationViewModel, isChecked: Boolean) {
        val notification = stateMapper.mapUpdate(vm.copy(checked = isChecked))

        disposables += updateNotificationUseCase.execute(notification)
                .observeOnUI()
                .subscribe({

                }, {

                })
    }
}