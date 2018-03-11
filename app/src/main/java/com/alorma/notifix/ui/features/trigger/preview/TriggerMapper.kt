package com.alorma.notifix.ui.features.trigger.preview

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import javax.inject.Inject

class TriggerMapper @Inject constructor() {
    fun map(it: NotificationTrigger): TriggerPreviewState.Success = when (it.payload) {
        is NotificationTriggerPayload.NumberPayload.PhonePayload -> mapPhone(it.payload)
        is NotificationTriggerPayload.NumberPayload.SmsPayload -> mapSms(it.payload)
        is NotificationTriggerPayload.TimePayload -> TriggerPreviewState.Success.Time()
        is NotificationTriggerPayload.ZonePayload -> TriggerPreviewState.Success.Zone()
    }

    private fun mapPhone(it: NotificationTriggerPayload.NumberPayload.PhonePayload) = TriggerPreviewState.Success.Phone(it.uri, it.name, it.phone, it.avatar)

    private fun mapSms(it: NotificationTriggerPayload.NumberPayload.SmsPayload) = TriggerPreviewState.Success.Sms(it.uri, it.name, it.phone, it.avatar)
}