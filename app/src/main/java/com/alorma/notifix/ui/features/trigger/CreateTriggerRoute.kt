package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.ui.commons.Route

sealed class CreateTriggerRoute : Route()
class SelectContact: CreateTriggerRoute()