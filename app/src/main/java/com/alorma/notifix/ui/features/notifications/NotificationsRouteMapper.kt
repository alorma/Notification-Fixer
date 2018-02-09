package com.alorma.notifix.ui.features.notifications

import javax.inject.Inject

class NotificationsRouteMapper @Inject constructor(){
    fun mapCreate() = CreateNotification()
}