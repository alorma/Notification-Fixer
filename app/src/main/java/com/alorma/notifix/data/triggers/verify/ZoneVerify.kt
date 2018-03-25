package com.alorma.notifix.data.triggers.verify

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.PayloadLauncher
import javax.inject.Inject

class ZoneVerify @Inject constructor() : TriggerVerify() {
    override fun verify(it: NotificationTrigger, payloadLauncher: PayloadLauncher): Boolean {
        throw Exception() // TODO --> Resolve position
    }
}