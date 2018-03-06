package com.alorma.notifix.ui.features.trigger.time

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.State
import com.alorma.notifix.ui.features.trigger.TriggerRoute

sealed class CreateTimeTriggerAction : Action() {
    class TimeSelectedAction(val hour: Int, val minute: Int) : CreateTimeTriggerAction()
}

sealed class CreateTimeTriggerState : State()

interface CreateTimeTriggerView : BaseView<CreateTimeTriggerState, TriggerRoute>