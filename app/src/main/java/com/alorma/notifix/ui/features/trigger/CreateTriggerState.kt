package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.ui.commons.State

sealed class CreateTriggerState : State()
class RequestContactPermission(): CreateTriggerState()
class DeniedPermissionMessage(): CreateTriggerState()
class DeniedAlwaysPermissionMessage(): CreateTriggerState()