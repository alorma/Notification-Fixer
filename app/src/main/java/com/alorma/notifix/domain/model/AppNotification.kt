package com.alorma.notifix.domain.model

data class AppNotification(val id: Int,
                           val title: String,
                           val text: String?,
                           val color: Int,
                           val triggerId: Long?,
                           val checked: Boolean?) {
    companion object {
        const val NO_ID = -1
    }
}