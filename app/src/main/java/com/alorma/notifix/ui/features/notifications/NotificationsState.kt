package com.alorma.notifix.ui.features.notifications

import com.alorma.notifix.ui.commons.State

sealed class NotificationsState : State()
data class ShowNotifications(val list: List<NotificationViewModel>): NotificationsState()