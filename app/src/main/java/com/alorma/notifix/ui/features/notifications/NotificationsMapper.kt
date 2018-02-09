package com.alorma.notifix.ui.features.notifications

class NotificationsMapper {
    fun mapSuccess(items: Int) = ShowNotifications((0..items).map {
        NotificationViewModel(it, "Notification $it", it % 2 == 0)
    })
}