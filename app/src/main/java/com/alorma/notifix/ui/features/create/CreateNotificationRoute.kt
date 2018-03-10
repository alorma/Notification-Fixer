package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.commons.Route

sealed class CreateNotificationRoute : Route()
class SuccessGoBack : CreateNotificationRoute()

sealed class ConfigureTrigger : CreateNotificationRoute() {
    class SmsTrigger : ConfigureTrigger()
    class PhoneTrigger : ConfigureTrigger()
    class TimeTrigger : ConfigureTrigger()
    class ZoneTrigger : ConfigureTrigger()
}