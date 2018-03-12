package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.repository.NotificationsRepository
import com.alorma.notifix.domain.repository.TriggersRepository
import io.reactivex.Single
import javax.inject.Inject

class ObtainNotificationsUseCase @Inject constructor(
        private val notificationsRepository: NotificationsRepository,
        private val triggersRepository: TriggersRepository) {

    fun execute(): Single<List<AppNotification>> = notificationsRepository.getNotifications()
            .flatMapIterable { it }
            .flatMapSingle { notification -> checkTrigger(notification) }
            .toList()

    private fun checkTrigger(notification: AppNotification): Single<AppNotification>? {
        return notification.triggerId?.let {
            mapTrigger(it).map { notification.copy(trigger = it) }
        } ?: Single.just(notification)
    }

    private fun mapTrigger(it: Int): Single<NotificationTrigger> =
            triggersRepository.getById(it)
}