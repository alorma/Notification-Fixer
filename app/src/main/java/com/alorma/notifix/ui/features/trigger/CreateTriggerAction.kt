package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.domain.model.Trigger
import com.alorma.notifix.ui.commons.Action

sealed class CreateTriggerAction : Action()
data class OnTriggerSelected(val trigger: Trigger) : CreateTriggerAction()