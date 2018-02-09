package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BasePresenter
import com.alorma.notifix.ui.commons.OnCreate

class NotificationsPresenter(private val mapper: NotificationsMapper)
    : BasePresenter<NotificationsState, NotificationsRoute, NotificationsView>() {

    override fun onAction(action: Action) = when (action) {
        is OnCreate -> loadNotifications()
        else -> { }
    }

    private fun loadNotifications() {
        render(mapper.mapSuccess(9))
    }
}