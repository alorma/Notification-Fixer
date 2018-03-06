package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.domain.model.AppNotification
import javax.inject.Inject

class NotificationsStateMapper @Inject constructor() {
    fun mapSuccessState(items: List<AppNotification>) = ShowNotifications(items.map {
        NotificationViewModel(it.id, it.title, it.text, it.checked == true, it.color)
    })

    fun mapUpdate(it: NotificationViewModel) = AppNotification(it.id, it.title, it.text, it.color, null, it.checked)
}