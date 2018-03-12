package com.alorma.notifix.ui.features.notifications

data class NotificationViewModel(val id: Int, val title: String, val text: String?,
                                 val checked: Boolean, val color: Int, val trigger: TriggerViewModel?)

data class TriggerViewModel(val id: Int)