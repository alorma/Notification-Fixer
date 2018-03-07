package com.alorma.notifix.data.notitications

import android.util.Log
import com.alorma.notifix.data.database.dao.NotificationDao
import com.alorma.notifix.data.database.dao.TriggersDao
import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.data.database.entity.TriggerEntity
import com.alorma.notifix.domain.model.*
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CacheNotificationsDataSource @Inject constructor(private val notificationDao: NotificationDao,
                                                       private val triggerDao: TriggersDao,
                                                       private val mapper: NotificationsDataMapper,
                                                       private val gson: Gson) {

    fun getNotifications(): Flowable<List<AppNotification>> = notificationDao.getNotifications().map {
        mapper.map(it)
    }

    fun getEnabledNotifications(): Flowable<List<AppNotification>> = notificationDao.getNotifications()
            .map { mapper.mapEnabled(it) }

    fun getEnabledNotifications(trigger: PayloadLauncher): Flowable<AppNotification> {
        val type = when (trigger) {
            is PayloadLauncher.Phone -> TriggerType.PHONE
            is PayloadLauncher.Sms -> TriggerType.SMS
            is PayloadLauncher.Time -> TriggerType.TIME
            is PayloadLauncher.Zone -> TriggerType.ZONE
        }
        return triggerDao.getTriggers(type).flatMapIterable {
            it.filter { isValidTrigger(it, trigger) }.filter { it.id != null }
        }.map { it.id }
                .flatMap {
                    obtainNotificationsFromTrigger(it)
                }
                .map { mapper.map(it) }
    }

    private fun isValidTrigger(it: TriggerEntity, trigger: PayloadLauncher): Boolean =
            when (it.type) {
                TriggerType.PHONE -> isValidPhoneTrigger(trigger, it)
                else -> false
            }

    private fun isValidPhoneTrigger(trigger: PayloadLauncher, it: TriggerEntity): Boolean {
        return when (trigger) {
            is PayloadLauncher.Phone -> {
                val clazz = NotificationTriggerPayload.NumberPayload.PhonePayload::class.java
                val phone = gson.fromJson(it.payload, clazz).phone
                checkPhone(phone, trigger.phone) ||checkPhone(trigger.phone, phone)
            }
            else -> false
        }
    }

    private fun checkPhone(phone: String, trigger: String) =
            phone.trim().replace(" ", "").replace("-", "").contains(trigger.trim()
                    .replace(" ", "").replace("-", ""))

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