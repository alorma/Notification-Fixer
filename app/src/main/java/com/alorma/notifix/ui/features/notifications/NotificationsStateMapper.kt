package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.NotificationTrigger
import javax.inject.Inject

class NotificationsStateMapper @Inject constructor() {
    fun mapSuccessState(items: List<AppNotification>) = ShowNotifications(items.map {
        NotificationViewModel(it.id, it.title, it.text, it.checked == true, it.color,
                it.trigger?.let { mapTrigger(it) })
    })

    private fun mapTrigger(it: NotificationTrigger): TriggerViewModel
            = TriggerViewModel(it.id ?: 0)

    fun mapUpdate(it: NotificationViewModel) = AppNotification(it.id, it.title, it.text, it.color,
            it.trigger?.id, null, it.checked)
}