package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.features.trigger.TriggerType

sealed class CreateNotificationAction : Action()

data class OnTriggerSelected(val type: TriggerType) : CreateNotificationAction()

data class NewNotificationAction(val title: String,
                                 val text: String,
                                 val checked: Boolean,
                                 val color: Int) : CreateNotificationAction()

data class PreviewNotificationAction(val title: String,
                                     val text: String,
                                     val checked: Boolean,
                                     val color: Int) : CreateNotificationAction()

class AddTriggerAction: CreateNotificationAction()