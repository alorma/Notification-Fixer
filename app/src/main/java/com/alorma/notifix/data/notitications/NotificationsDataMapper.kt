package com.alorma.notifix.data.notitications

import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.domain.model.AppNotification
import javax.inject.Inject

class NotificationsDataMapper @Inject constructor() {
    fun map(it: List<NotificationEntity>): List<AppNotification> = it.map {
        map(it)
    }

    private fun map(it: NotificationEntity): AppNotification = AppNotification(it.id, it.text, it.checked, Any())
}