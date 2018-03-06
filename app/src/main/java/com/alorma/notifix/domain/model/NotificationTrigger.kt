package com.alorma.notifix.domain.model

sealed class NotificationTriggerPayload {
    sealed class NumberPayload(val uri: String) : NotificationTriggerPayload() {
        class PhonePayload(uri: String) : NumberPayload(uri)
        class SmsPayload(uri: String) : NumberPayload(uri)
    }

    class TimePayload(val hour: Int, val minute: Int) : NotificationTriggerPayload()
    class ZonePayload(val lat: Double, val lon: Double) : NotificationTriggerPayload()
}

class NotificationTrigger(val payload: NotificationTriggerPayload)