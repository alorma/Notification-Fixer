package com.alorma.notifix.ui.features.trigger.zone

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.State
import com.alorma.notifix.ui.features.trigger.TriggerRoute

sealed class CreateZoneTriggerAction : Action() {
    class OnMapReadyAction : CreateZoneTriggerAction()
    class SelectedLocation(val lat: Double, val lon: Double): CreateZoneTriggerAction()
}

sealed class CreateZoneTriggerState : State() {
    sealed class Location : CreateZoneTriggerState() {
        class Allowed : Location()
        class Denied : Location()
        class DeniedAlways : Location()
    }
}

interface CreateZoneTriggerView : BaseView<CreateZoneTriggerState, TriggerRoute>