package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.domain.model.Contact
import com.alorma.notifix.ui.commons.State

sealed class CreateTriggerState : State()
class DeniedPermissionMessage: CreateTriggerState()
class DeniedAlwaysPermissionMessage: CreateTriggerState()
class ContactLoaded(val contact: Contact): CreateTriggerState()