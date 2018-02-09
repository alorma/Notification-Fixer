package com.alorma.notifix.ui.features.notifications

import javax.inject.Inject

class NotificationsMapper @Inject constructor(){
    fun mapSuccess(items: Int) = ShowNotifications((0..items).map {
        NotificationViewModel(it, "Notification $it", it % 2 == 0)
    })
}