package com.alorma.notifix.ui.features.trigger.preview

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import javax.inject.Inject

class TriggerMapper @Inject constructor() {
    fun map(it: NotificationTrigger): TriggerPreviewState.Success = when (it.payload) {
        is NotificationTriggerPayload.NumberPayload.PhonePayload -> TriggerPreviewState.Success.Phone()
        is NotificationTriggerPayload.NumberPayload.SmsPayload -> TriggerPreviewState.Success.Sms()
        is NotificationTriggerPayload.TimePayload -> TriggerPreviewState.Success.Time()
        is NotificationTriggerPayload.ZonePayload -> TriggerPreviewState.Success.Zone()
    }
}