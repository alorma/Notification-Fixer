package com.alorma.notifix.domain.model

data class CreateAppNotification(val title: String, val text: String?, val color: Int,
                                 val checked: Boolean, val triggerId: Int?)