package com.alorma.notifix.ui.features.create

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.CreateAppNotification
import java.util.*
import javax.inject.Inject

class CreateNotificationActionMapper @Inject constructor() {
    fun mapCreate(it: NewNotificationAction) = CreateAppNotification(it.title, it.text, it.checked)
    fun mapPreview(it: PreviewNotificationAction) = AppNotification(Random().nextInt(), it.title, it.text, it.checked, Any())
}