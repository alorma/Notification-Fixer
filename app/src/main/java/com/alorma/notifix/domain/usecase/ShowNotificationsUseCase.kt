package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.PayloadLauncher
import com.alorma.notifix.domain.repository.NotificationsRepository
import io.reactivex.Completable
import javax.inject.Inject

class ShowNotificationsUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository) {

    fun execute(): Completable = notificationsRepository.showNotifications()

    fun execute(trigger: PayloadLauncher): Completable = notificationsRepository.showNotifications(trigger)
}