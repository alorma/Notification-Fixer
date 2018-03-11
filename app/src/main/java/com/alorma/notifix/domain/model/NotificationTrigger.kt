package com.alorma.notifix.domain.model

sealed class NotificationTriggerPayload {
    sealed class NumberPayload(val userId: String, val name: String, val phone: String, val avatar: String?) : NotificationTriggerPayload() {
        class PhonePayload(userId: String, name: String, phone: String, avatar: String?) : NumberPayload(userId, name, phone, avatar)
        class SmsPayload(userId: String, name: String, phone: String, avatar: String?) : NumberPayload(userId, name, phone, avatar)
    }

    class TimePayload(val hour: Int, val minute: Int) : NotificationTriggerPayload()
    class ZonePayload(val lat: Double, val lon: Double) : NotificationTriggerPayload()
}

data class NotificationTrigger(val id: Int? = null, val payload: NotificationTriggerPayload)

sealed class PayloadLauncher {
    class Phone(val phone: String) : PayloadLauncher()
    class Sms(val phone: String) : PayloadLauncher()
    class Time(val hour: Int, val minute: Int) : PayloadLauncher()
    class Zone : PayloadLauncher()
}

object TriggerType {
    const val PHONE = "phone"
    const val SMS = "sms"
    const val TIME = "time"
    const val ZONE = "zone"
}
