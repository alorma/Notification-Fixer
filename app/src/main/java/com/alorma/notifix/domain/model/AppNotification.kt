package com.alorma.notifix.domain.model

data class AppNotification(val id: Int,
                           val title: String,
                           val text: String?,
                           val color: Int,
                           val checked: Boolean,
                           val condition: Any)