package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.repository.NotificationsRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ObtainNotificationsUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {
    fun execute(): Flowable<List<AppNotification>> = notificationsRepository.getNotifications()
}