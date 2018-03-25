package com.alorma.notifix.data.triggers.setup

import com.alorma.notifix.domain.model.NotificationTriggerPayload
import javax.inject.Inject

class ZoneTriggerSetup @Inject constructor():
        TriggerSetup<NotificationTriggerPayload.ZonePayload>() {

    override fun setup(payload: NotificationTriggerPayload.ZonePayload, triggerId: Int) {

    }
}