package com.alorma.notifix.data.triggers.verify

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.model.PayloadLauncher
import javax.inject.Inject

class TimeVerify @Inject constructor() : TriggerVerify() {
    override fun verify(it: NotificationTrigger, payloadLauncher: PayloadLauncher): Boolean {
        return (it.payload as? NotificationTriggerPayload.TimePayload)?.let {
            (payloadLauncher as? PayloadLauncher.Time)?.let {
                it.hour == payloadLauncher.hour && it.minute == payloadLauncher.minute
            } ?: false
        } ?: false
    }

}