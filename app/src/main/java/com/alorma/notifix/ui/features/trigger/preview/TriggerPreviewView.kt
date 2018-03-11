package com.alorma.notifix.ui.features.trigger.preview

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class TriggerPreviewState : State() {
    sealed class Success: TriggerPreviewState() {
        data class Phone(val userId: String, val name: String, val phone: String, val photo: String?) : Success()
        data class Sms(val userId: String, val name: String, val phone: String, val photo: String?) : Success()
        data class Time(val hour: Int, val minute: Int) : Success()
        data class Zone(val lat: Double, val lon: Double) : Success()
    }
}

sealed class TriggerPreviewRoute : Route()

sealed class TriggerPreviewAction : Action() {
    data class LoadTrigger(val id: Long): TriggerPreviewAction()
}

interface TriggerPreviewView: BaseView<TriggerPreviewState, TriggerPreviewRoute>
