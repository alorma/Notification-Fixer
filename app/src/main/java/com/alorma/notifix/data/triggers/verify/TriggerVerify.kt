package com.alorma.notifix.data.triggers.verify

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.PayloadLauncher

abstract class TriggerVerify {
    abstract fun verify (it: NotificationTrigger, payloadLauncher: PayloadLauncher): Boolean
}