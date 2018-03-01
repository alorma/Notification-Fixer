package com.alorma.notifix.ui.features.trigger

import android.net.Uri
import com.alorma.notifix.ui.commons.Action

sealed class CreateTriggerAction : Action()
class RequestContactAction : CreateTriggerAction()
class ContactImportAction(val uri: Uri) : CreateTriggerAction()