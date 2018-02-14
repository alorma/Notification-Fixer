package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.repository.NotificationsRepository
import io.reactivex.Completable
import javax.inject.Inject

class DismissNotificationUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {
    fun execute(notificationId: Int) : Completable = notificationsRepository.dismissNotification(notificationId)
}