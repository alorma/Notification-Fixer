package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.ui.commons.Route

open class TriggerRoute : Route() {
    companion object {
        const val TRIGGER_ID = "trigger_id"
    }
    class Success(val triggerId: Long): TriggerRoute()
    class Cancel: TriggerRoute()
    class Error: TriggerRoute()
}