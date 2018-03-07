package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.repository.NotificationsRepository
import io.reactivex.Completable
import javax.inject.Inject

class ShowNotificationsUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {

    fun execute(): Completable = notificationsRepository.showNotifications()

    fun execute(triggerId: Int): Completable = notificationsRepository.showNotifications(triggerId)
}