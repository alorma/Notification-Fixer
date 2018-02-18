package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.ui.commons.Action

sealed class CreateTriggerAction : Action()

sealed class Trigger : CreateTriggerAction()

class SMS : Trigger()
class PHONE : Trigger()
class TIME : Trigger()
class ZONE : Trigger()
class UNKNOW : Trigger()