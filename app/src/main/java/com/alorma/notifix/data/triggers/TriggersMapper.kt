package com.alorma.notifix.data.triggers

import com.alorma.notifix.data.database.entity.TriggerEntity
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.google.gson.Gson
import javax.inject.Inject

class TriggersMapper @Inject constructor(private val gson: Gson) {

    companion object {
        private const val PHONE = "phone"
        private const val SMS = "sms"
        private const val TIME = "time"
        private const val ZONE = "zone"
    }

    fun map(trigger: NotificationTrigger): TriggerEntity =
            TriggerEntity(type = when (trigger.payload) {
                is NotificationTriggerPayload.NumberPayload.PhonePayload -> PHONE
                is NotificationTriggerPayload.NumberPayload.SmsPayload -> SMS
                is NotificationTriggerPayload.TimePayload -> TIME
                is NotificationTriggerPayload.ZonePayload -> ZONE
            }, payload = gson.toJson(trigger.payload))
}