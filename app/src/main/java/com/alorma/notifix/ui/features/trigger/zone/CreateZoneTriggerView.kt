package com.alorma.notifix.ui.features.trigger.zone

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class CreateZoneTriggerAction : Action() {
    class OnMapReadyAction : CreateZoneTriggerAction()
    class SelectedLocation(val lat: Double, val lon: Double): CreateZoneTriggerAction()
}

sealed class CreateZoneTriggerRoute : Route() {

}

sealed class CreateZoneTriggerState : State() {
    sealed class Location : CreateZoneTriggerState() {
        class Allowed : Location()
        class Denied : Location()
        class DeniedAlways : Location()
    }
}

interface CreateZoneTriggerView : BaseView<CreateZoneTriggerState, CreateZoneTriggerRoute>