package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import javax.inject.Inject

class NotificationsStateMapper @Inject constructor() {
    fun mapSuccessState(items: List<AppNotification>) = ShowNotifications(items.map {
        NotificationViewModel(it.id, it.title, it.text, it.checked == true, it.color,
                it.trigger?.let { mapTrigger(it) })
    })

    private fun mapTrigger(it: NotificationTrigger): TriggerViewModel? = it.id?.let { _ ->
        when (it.payload) {
            is NotificationTriggerPayload.NumberPayload.PhonePayload -> TriggerViewModel.Phone(it.id, it.payload.avatar)
            is NotificationTriggerPayload.NumberPayload.SmsPayload -> TriggerViewModel.Sms(it.id, it.payload.avatar)
            is NotificationTriggerPayload.TimePayload -> TriggerViewModel.Time(it.id)
            is NotificationTriggerPayload.ZonePayload -> TriggerViewModel.Zone(it.id, it.payload.lat, it.payload.lon)
        }
    }

    fun mapError(it: Throwable) = Invalid(it)

    fun mapUpdate(it: NotificationViewModel) = AppNotification(it.id, it.title, it.text, it.color,
            it.trigger?.id, null, it.checked)
}