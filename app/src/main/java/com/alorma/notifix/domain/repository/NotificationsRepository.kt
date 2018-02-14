package com.alorma.notifix.domain.repository

import com.alorma.notifix.data.framework.DisplayNotificationDataSource
import com.alorma.notifix.data.notitications.CacheNotificationsDataSource
import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.domain.model.CreateAppNotification
import com.alorma.notifix.ui.utils.subscribeOnIO
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class NotificationsRepository @Inject constructor
(private val cacheNotificationsDataSource: CacheNotificationsDataSource,
 private val displayNotificationDataSource: DisplayNotificationDataSource) {

    fun getNotifications(): Flowable<List<AppNotification>> = cacheNotificationsDataSource.getNotifications().subscribeOnIO()

    fun showNotifications(): Completable = cacheNotificationsDataSource.getEnabledNotifications()
            .flatMapCompletable {
                displayNotificationDataSource.show(it)
            }

    fun insertNotification(appNotification: CreateAppNotification): Completable
            = cacheNotificationsDataSource.insert(appNotification)
            .flatMapCompletable {
                displayNotificationDataSource.showOne(it)
            }
            .subscribeOnIO()

    fun showPreview(appNotification: AppNotification): Completable = displayNotificationDataSource.showPreview(appNotification)

    fun dismissNotification(notificationId: Int): Completable = displayNotificationDataSource.dismiss(notificationId).subscribeOnIO()
}