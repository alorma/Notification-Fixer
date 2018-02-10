package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.repository.NotificationsRepository
import javax.inject.Inject

class ShowNotificationUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {

    fun showPreview(appNotification: AppNotification) = notificationsRepository.showPreview(appNotification)
}