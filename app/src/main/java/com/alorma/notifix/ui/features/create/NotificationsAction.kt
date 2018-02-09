package com.alorma.notifix.ui.features.create

import com.alorma.notifix.ui.commons.Action

sealed class NotificationsAction : Action()
class OnCreateSucces: NotificationsAction()