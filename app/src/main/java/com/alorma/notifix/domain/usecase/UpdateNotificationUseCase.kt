package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.repository.NotificationsRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateNotificationUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {

    fun execute(appNotification: AppNotification) : Completable = Completable.concatArray(
            notificationsRepository.updateNotification(appNotification)
    )
}