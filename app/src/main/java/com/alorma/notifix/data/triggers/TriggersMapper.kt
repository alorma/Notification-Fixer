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

    fun map(trigger: TriggerEntity): NotificationTrigger = NotificationTrigger(trigger.id, when (trigger.type) {
        TriggerType.PHONE -> buildPhonePayload(trigger)
        TriggerType.SMS -> buildSmsPayload(trigger)
        TriggerType.TIME -> buildTimePayload(trigger)
        TriggerType.ZONE -> buildZonePayload(trigger)
        else -> throw Exception()
    })

    private fun buildPhonePayload(trigger: TriggerEntity): NotificationTriggerPayload.NumberPayload.PhonePayload =
            gson.fromJson(trigger.payload, NotificationTriggerPayload.NumberPayload.PhonePayload::class.java)

    private fun buildSmsPayload(trigger: TriggerEntity): NotificationTriggerPayload.NumberPayload.SmsPayload =
            gson.fromJson(trigger.payload, NotificationTriggerPayload.NumberPayload.SmsPayload::class.java)

    private fun buildTimePayload(trigger: TriggerEntity): NotificationTriggerPayload.TimePayload =
            gson.fromJson(trigger.payload, NotificationTriggerPayload.TimePayload::class.java)

    private fun buildZonePayload(trigger: TriggerEntity): NotificationTriggerPayload.ZonePayload =
            gson.fromJson(trigger.payload, NotificationTriggerPayload.ZonePayload::class.java)
}