package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.domain.model.AppNotification
import javax.inject.Inject

class NotificationsStateMapper @Inject constructor() {
    fun mapSuccess(items: List<AppNotification>) = ShowNotifications(items.map {
        NotificationViewModel(it.id, it.title, it.checked)
    })
}