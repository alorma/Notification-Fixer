package com.alorma.notifix.domain.model

data class AppNotification(val id: Int, val text: String, val checked: Boolean, val condition: Any)