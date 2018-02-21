package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.features.trigger.*
import javax.inject.Inject

class CreateNotificationRouteMapper @Inject constructor() {
    fun mapComplete() = SuccessGoBack()
    fun mapTrigger(type: TriggerType): CreateNotificationRoute = when(type) {
        Sms -> SmsTrigger()
        Phone -> PhoneTrigger()
        Time -> TimeTrigger()
        Zone -> ZoneTrigger()
    }
}