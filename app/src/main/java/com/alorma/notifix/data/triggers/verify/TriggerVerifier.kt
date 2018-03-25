package com.alorma.notifix.data.triggers.verify

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.PayloadLauncher
import javax.inject.Inject

class TriggerVerifier @Inject constructor(private val numberVerify: NumberVerify,
                                          private val timeVerify: TimeVerify,
                                          private val zoneVerify: ZoneVerify) {

    fun validator(it: NotificationTrigger, payloadLauncher: PayloadLauncher): Boolean {
        return when (payloadLauncher) {
            is PayloadLauncher.Phone -> numberVerify.verify(it, payloadLauncher)
            is PayloadLauncher.Sms -> numberVerify.verify(it, payloadLauncher)
            is PayloadLauncher.Time -> timeVerify.verify(it, payloadLauncher)
            is PayloadLauncher.Zone -> zoneVerify.verify(it, payloadLauncher)
        }
    }
}