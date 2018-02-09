package com.alorma.notifix.ui.commons

open class Action

sealed class LifeCycleAction : Action()
class OnCreate : LifeCycleAction()
class OnStop : LifeCycleAction()