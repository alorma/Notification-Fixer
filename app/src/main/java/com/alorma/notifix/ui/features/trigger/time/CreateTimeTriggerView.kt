package com.alorma.notifix.ui.features.trigger.time

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class CreateTimeTriggerAction : Action() {

}

sealed class CreateTimeTriggerRoute : Route() {

}

sealed class CreateTimeTriggerState : State() {

}

interface CreateTimeTriggerView : BaseView<CreateTimeTriggerState, CreateTimeTriggerRoute>