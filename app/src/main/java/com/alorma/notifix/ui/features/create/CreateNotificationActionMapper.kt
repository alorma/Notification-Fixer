package com.alorma.notifix.ui.features.create

import com.alorma.notifix.domain.model.CreateAppNotification
import javax.inject.Inject

class CreateNotificationActionMapper @Inject constructor() {
    fun mapCreate(it: NewNotificationAction) = CreateAppNotification(it.title, it.text, it.checked)
}