package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.commons.Route

sealed class CreateNotificationRoute : Route()
class SuccessGoBack: CreateNotificationRoute()

class AddTrigger: CreateNotificationRoute()