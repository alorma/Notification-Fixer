package com.alorma.notifix.ui.features.trigger

sealed class TriggerType
object Sms: TriggerType()
object Phone: TriggerType()
object Time: TriggerType()
object Zone: TriggerType()