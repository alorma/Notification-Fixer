package com.alorma.notifix.ui.features.create

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.AppNotification.Companion.NO_ID
import com.alorma.notifix.domain.model.CreateAppNotification
import javax.inject.Inject

class CreateNotificationActionMapper @Inject constructor() {
    fun mapCreate(it: NewNotificationAction) = CreateAppNotification(it.title, it.text, it.color, it.checked)
    fun mapPreview(it: PreviewNotificationAction) = AppNotification(NO_ID, it.title, it.text, it.color, it.checked, Any())
    fun mapPreviewNotificationId(): Int = NO_ID
}