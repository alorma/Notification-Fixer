package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.domain.model.Trigger
import com.alorma.notifix.ui.commons.State

sealed class CreateTriggerState : State()

data class TriggersSuccess(val triggers: List<Trigger>) : CreateTriggerState()