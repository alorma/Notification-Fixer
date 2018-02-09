package com.alorma.notifix.domain.repository

import com.alorma.notifix.domain.model.AppNotification
import com.alorma.notifix.ui.utils.subscribeOnIO
import io.reactivex.Single
import javax.inject.Inject

class NotificationsRepository @Inject constructor() {
    fun getNotifications(): Single<List<AppNotification>> = Single.fromCallable {
        (0..10).map {
            AppNotification(it, "Notification $it", it % 2 == 0, Any())
        }
    }.subscribeOnIO()
}