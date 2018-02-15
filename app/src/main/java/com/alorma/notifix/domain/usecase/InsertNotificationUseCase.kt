package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.CreateAppNotification
import com.alorma.notifix.domain.repository.NotificationsRepository
import io.reactivex.Completable
import javax.inject.Inject

class InsertNotificationUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {

    fun execute(appNotification: CreateAppNotification): Completable = notificationsRepository.insertNotification(appNotification)
}