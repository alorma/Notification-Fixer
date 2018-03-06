package com.alorma.notifix.domain.model

sealed class NotificationTriggerPayload {
    sealed class NumberPayload(val number: String) : NotificationTriggerPayload() {
        class PhonePayload(phone: String) : NumberPayload(phone)
        class SmsPayload(phone: String) : NumberPayload(phone)
    }

    class TimePayload : NotificationTriggerPayload()
    class ZonePayload(val lat: Double, val lon: Double) : NotificationTriggerPayload()
}

class NotificationTrigger(val payload: NotificationTriggerPayload)