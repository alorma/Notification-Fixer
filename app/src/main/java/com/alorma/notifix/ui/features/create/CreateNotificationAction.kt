package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.commons.Action

sealed class CreateNotificationAction : Action()
data class NewNotificationAction(val title: String,
                                 val text: String,
                                 val checked: Boolean) : CreateNotificationAction()