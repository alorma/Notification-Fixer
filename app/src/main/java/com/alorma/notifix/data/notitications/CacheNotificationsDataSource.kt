package com.alorma.notifix.data.notitications

import com.alorma.notifix.data.database.dao.NotificationDao
import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.CreateAppNotification
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CacheNotificationsDataSource @Inject constructor(private val notificationDao: NotificationDao,
                                                       private val mapper: NotificationsDataMapper) {

    fun getNotifications(): Flowable<List<AppNotification>> = notificationDao.getNotifications().map {
        mapper.map(it)
    }

    fun getEnabledNotifications(): Flowable<List<AppNotification>> = notificationDao.getNotifications()
            .map { mapper.mapEnabled(it) }

    fun getEnabledNotifications(trigger: Int): Flowable<AppNotification> =
            obtainNotificationsFromTrigger(trigger).map { mapper.map(it) }

    private fun obtainNotificationsFromTrigger(it: Int): Flowable<NotificationEntity> =
            notificationDao.getNotificationByTrigger(it.toLong()).toFlowable()
                    .flatMapIterable { it }
                    .filter { it.checked }

    fun insert(appNotification: CreateAppNotification): Single<AppNotification> = Single.defer {
        val rowId = notificationDao.insert(mapper.mapInsert(appNotification))
        notificationDao.getNotification(rowId)
    }.map { mapper.map(it) }

    fun update(appNotification: AppNotification): Completable = Completable.fromCallable {
        notificationDao.update(mapper.mapUpdate(appNotification))
    }
}