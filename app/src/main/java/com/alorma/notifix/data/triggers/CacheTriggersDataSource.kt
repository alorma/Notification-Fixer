package com.alorma.notifix.data.triggers

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alorma.notifix.data.database.dao.TriggersDao
import com.alorma.notifix.data.database.entity.TriggerEntity
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.model.PayloadLauncher
import com.alorma.notifix.service.TimeNotificationService
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheTriggersDataSource @Inject constructor(
        private val context: Context,
        private val triggersDao: TriggersDao,
        private val triggersMapper: TriggersMapper) {

    companion object {
        private const val REQUEST = 112
    }

    fun save(trigger: NotificationTrigger): Single<Long> = Single.fromCallable {
        val entity: TriggerEntity = triggersMapper.map(trigger)
        triggersDao.insert(entity).apply {
            setupTrigger(trigger, this)
        }
    }

    private fun setupTrigger(trigger: NotificationTrigger, triggerId: Long) {
        when (trigger.payload) {
            is NotificationTriggerPayload.TimePayload -> setupTimeTrigger(trigger.payload, triggerId)
        }
    }

    private fun setupTimeTrigger(payload: NotificationTriggerPayload.TimePayload, triggerId: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val millisRepeat = TimeUnit.SECONDS.toMillis(30)

        val intent = Intent(context, TimeNotificationService::class.java).apply {
            putExtras(Bundle().apply {
                putLong(TimeNotificationService.TRIGGER_ID, triggerId)
            })
        }
        val pending = PendingIntent.getService(context, REQUEST, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val time = Calendar.getInstance().apply {
            set(Calendar.HOUR, payload.hour)
            set(Calendar.MINUTE, payload.minute)
        }.timeInMillis

        am.setRepeating(RTC_WAKEUP, time, millisRepeat, pending)
    }

    fun get(payloadLauncher: PayloadLauncher): Single<NotificationTrigger> =
            triggersDao.getTriggers().flatMapIterable { it }.map {
                triggersMapper.map(it)
            }.filter {
                        when (payloadLauncher) {
                            is PayloadLauncher.Phone -> checkPhoneTrigger(it, payloadLauncher)
                            is PayloadLauncher.Sms -> checkSmsTrigger(it, payloadLauncher)
                            is PayloadLauncher.Time -> checkTimeTrigger(it, payloadLauncher)
                            is PayloadLauncher.Zone -> checkZoneTrigger(it, payloadLauncher)
                        }
                    }.firstOrError()

    private fun checkPhoneTrigger(it: NotificationTrigger, payloadLauncher: PayloadLauncher.Phone): Boolean =
            (it.payload as? NotificationTriggerPayload.NumberPayload.PhonePayload)?.let {
                checkPhone(payloadLauncher.phone, it.phone)
            } ?: false

    private fun checkSmsTrigger(it: NotificationTrigger, payloadLauncher: PayloadLauncher.Sms): Boolean =
            (it.payload as? NotificationTriggerPayload.NumberPayload.SmsPayload)?.let {
                checkPhone(payloadLauncher.phone, it.phone)
            } ?: false

    private fun checkPhone(phone: String, trigger: String) =
            phone.trim().replace(" ", "").replace("-", "").contains(trigger.trim()
                    .replace(" ", "").replace("-", ""))


    private fun checkTimeTrigger(it: NotificationTrigger, payloadLauncher: PayloadLauncher.Time): Boolean =
            (it.payload as? NotificationTriggerPayload.TimePayload)?.let {
                it.hour == payloadLauncher.hour && it.minute == payloadLauncher.minute
            } ?: false

    private fun checkZoneTrigger(it: NotificationTrigger, payloadLauncher: PayloadLauncher.Zone): Boolean =
            throw Exception() // TODO --> Resolve position
}