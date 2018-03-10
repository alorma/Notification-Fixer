package com.alorma.notifix.ui.features.trigger

sealed class TriggerType
object Phone: TriggerType()
object Sms: TriggerType()
object Time: TriggerType()
object Zone: TriggerType()