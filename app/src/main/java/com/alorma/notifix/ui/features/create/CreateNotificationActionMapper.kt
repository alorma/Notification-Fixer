package com.alorma.notifix.ui.features.create

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.AppNotification.Companion.NO_ID
import com.alorma.notifix.domain.model.CreateAppNotification
import javax.inject.Inject

class CreateNotificationActionMapper @Inject constructor() {
    fun mapCreate(it: NewNotificationAction, triggerId: Int?) = CreateAppNotification(it.title, it.text, it.color, it.checked, triggerId)
    fun mapPreview(it: PreviewNotificationAction) = AppNotification(NO_ID, it.title, it.text, it.color, null, null, null)
    fun mapPreviewNotificationId(): Int = NO_ID
}