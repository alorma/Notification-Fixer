package com.alorma.notifix.data.notitications

import com.alorma.notifix.data.database.dao.NotificationDao
import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.CreateAppNotification
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CacheNotificationsDataSource @Inject constructor(private val notificationDao: NotificationDao,
                                                       private val mapper: NotificationsDataMapper) {

    fun getNotifications(): Flowable<List<AppNotification>> = notificationDao.getNotifications().map {
        mapper.map(it)
    }

    fun insert(appNotification: CreateAppNotification): Completable = Completable.fromCallable {
        notificationDao.insert(mapper.mapInsert(appNotification))
    }
}