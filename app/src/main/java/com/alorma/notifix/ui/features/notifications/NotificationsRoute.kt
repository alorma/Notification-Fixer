package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.ui.commons.Route

sealed class NotificationsRoute : Route()
class CreateNotification : NotificationsRoute()