package com.alorma.notifix.data.triggers

import com.alorma.notifix.data.database.entity.TriggerEntity
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.model.TriggerType
import com.google.gson.Gson
import javax.inject.Inject

class TriggersMapper @Inject constructor(private val gson: Gson) {
    fun map(trigger: NotificationTrigger): TriggerEntity =
            TriggerEntity(type = when (trigger.payload) {
                is NotificationTriggerPayload.NumberPayload.PhonePayload -> TriggerType.PHONE
                is NotificationTriggerPayload.NumberPayload.SmsPayload -> TriggerType.SMS
                is NotificationTriggerPayload.TimePayload -> TriggerType.TIME
                is NotificationTriggerPayload.ZonePayload -> TriggerType.ZONE
            }, payload = gson.toJson(trigger.payload))
}