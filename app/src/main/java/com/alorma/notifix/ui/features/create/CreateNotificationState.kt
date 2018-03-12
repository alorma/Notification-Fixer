package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.commons.State

sealed class CreateNotificationState : State() {
    sealed class Trigger : CreateNotificationState() {
        class Any(val triggerId: Int): Trigger()
    }
}