package com.alorma.notifix.ui.features.trigger.preview

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import javax.inject.Inject

class TriggerMapper @Inject constructor() {
    fun map(it: NotificationTrigger): TriggerPreviewState.Success = when (it.payload) {
        is NotificationTriggerPayload.NumberPayload.PhonePayload -> mapPhone(it.payload)
        is NotificationTriggerPayload.NumberPayload.SmsPayload -> mapSms(it.payload)
        is NotificationTriggerPayload.TimePayload -> mapTime(it.payload)
        is NotificationTriggerPayload.ZonePayload -> mapZone(it.payload)
    }

    private fun mapPhone(it: NotificationTriggerPayload.NumberPayload.PhonePayload) = TriggerPreviewState.Success.Phone(it.userId, it.name, it.phone, it.avatar)

    private fun mapSms(it: NotificationTriggerPayload.NumberPayload.SmsPayload) = TriggerPreviewState.Success.Sms(it.userId, it.name, it.phone, it.avatar)

    private fun mapTime(it: NotificationTriggerPayload.TimePayload) = TriggerPreviewState.Success.Time(it.hour, it.minute)

    private fun mapZone(it: NotificationTriggerPayload.ZonePayload) = TriggerPreviewState.Success.Zone(it.lat, it.lon)
}