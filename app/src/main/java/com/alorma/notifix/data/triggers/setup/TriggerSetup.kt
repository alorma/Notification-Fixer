package com.alorma.notifix.data.triggers.setup

import com.alorma.notifix.domain.model.NotificationTriggerPayload

abstract class TriggerSetup<in T : NotificationTriggerPayload> {
    abstract fun setup(payload: T, triggerId: Int)
}