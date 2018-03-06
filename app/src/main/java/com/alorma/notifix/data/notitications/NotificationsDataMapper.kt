package com.alorma.notifix.data.notitications

import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.CreateAppNotification
import javax.inject.Inject

class NotificationsDataMapper @Inject constructor() {
    fun map(it: List<NotificationEntity>): List<AppNotification> = it.map {
        map(it)
    }

    fun mapEnabled(it: List<NotificationEntity>): List<AppNotification> = it.filter {
        it.checked
    }.map { map(it) }

    fun map(it: NotificationEntity): AppNotification = AppNotification(it.id
            ?: 0, it.title, it.text, it.color, it.trigger, it.checked)

    fun mapInsert(it: CreateAppNotification): NotificationEntity = NotificationEntity(title = it.title,
            text = it.text,
            color = it.color,
            trigger = it.triggerId,
            checked = it.checked)

    fun mapUpdate(it: AppNotification): NotificationEntity = NotificationEntity(it.id,
            it.title,
            it.text,
            it.color,
            it.triggerId,
            it.checked == true)
}