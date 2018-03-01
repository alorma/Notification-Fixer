package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.ui.commons.Action

sealed class CreateTriggerAction : Action()
class RequestContactAction : CreateTriggerAction()