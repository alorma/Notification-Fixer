package com.alorma.notifix.data.notitications

import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.CreateAppNotification
import javax.inject.Inject

class NotificationsDataMapper @Inject constructor() {
    fun map(it: List<NotificationEntity>): List<AppNotification> = it.map {
        map(it)
    }

    private fun map(it: NotificationEntity): AppNotification = AppNotification(it.id
            ?: 0, it.title, it.text, it.color, it.checked, Any())

    fun mapInsert(it: CreateAppNotification): NotificationEntity = NotificationEntity(title = it.title, text = it.text, color = it.color, checked = it.checked)
}