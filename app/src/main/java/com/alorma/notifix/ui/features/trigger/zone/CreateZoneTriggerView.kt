package com.alorma.notifix.ui.features.trigger.zone

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class CreateZoneTriggerAction : Action() {

}

sealed class CreateZoneTriggerRoute : Route() {

}

sealed class CreateZoneTriggerState : State() {

}

interface CreateZoneTriggerView : BaseView<CreateZoneTriggerState, CreateZoneTriggerRoute>