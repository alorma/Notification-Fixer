package com.alorma.notifix.ui.features.trigger.preview

import com.alorma.notifix.ui.commons.Action
import com.alorma.notifix.ui.commons.BaseView
import com.alorma.notifix.ui.commons.Route
import com.alorma.notifix.ui.commons.State

sealed class TriggerPreviewState : State() {
    sealed class Success: TriggerPreviewState() {
        class Phone: Success()
        class Sms: Success()
        class Time: Success()
        class Zone: Success()
    }
}

sealed class TriggerPreviewRoute : Route()

sealed class TriggerPreviewAction : Action() {
    data class LoadTrigger(val id: Long): TriggerPreviewAction()
}

interface TriggerPreviewView: BaseView<TriggerPreviewState, TriggerPreviewRoute>
