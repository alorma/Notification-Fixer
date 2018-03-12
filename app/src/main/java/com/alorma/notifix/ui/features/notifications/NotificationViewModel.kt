package com.alorma.notifix.ui.features.notifications

data class NotificationViewModel(val id: Int, val title: String, val text: String?,
                                 val checked: Boolean, val color: Int, val trigger: TriggerViewModel?)

sealed class TriggerViewModel(val id: Int) {
    class Phone(id: Int, val avatar: String?) : TriggerViewModel(id)
    class Sms(id: Int, val avatar: String?) : TriggerViewModel(id)
    class Time(id: Int) : TriggerViewModel(id)
    class Zone(id: Int, val lat: Double, val lon: Double) : TriggerViewModel(id)
}