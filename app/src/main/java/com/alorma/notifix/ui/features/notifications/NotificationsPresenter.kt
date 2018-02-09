package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.domain.usecase.ObtainNotificationsUseCase
import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.commons.OnCreate
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import javax.inject.Inject

class NotificationsPresenter @Inject constructor(
        private val obtainNotificationsUseCase: ObtainNotificationsUseCase,
        private val mapper: NotificationsMapper)
    : BasePresenter<NotificationsState, NotificationsRoute, NotificationsView>() {

    override fun onAction(action: Action) = when (action) {
        is OnCreate -> loadNotifications()
        else -> { }
    }

    private fun loadNotifications() {
        disposables += obtainNotificationsUseCase.execute()
                .observeOnUI()
                .subscribe({
                    render(mapper.mapSuccess(it))
                }, {})
    }
}