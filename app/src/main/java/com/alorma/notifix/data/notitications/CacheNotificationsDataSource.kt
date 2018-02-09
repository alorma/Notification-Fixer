package com.alorma.notifix.data.notitications

import com.alorma.notifix.data.database.dao.NotificationDao
import com.alorma.notifix.domain.model.AppNotification
import io.reactivex.Single
import javax.inject.Inject

class CacheNotificationsDataSource @Inject constructor(private val notificationDao: NotificationDao,
                                                       private val mapper: NotificationsDataMapper) {

    fun getNotifications(): Single<List<AppNotification>> = notificationDao.getNotifications().map {
        mapper.map(it)
    }
}