package com.alorma.notifix.data.triggers

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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

        val intent = Intent(context, TimeNotificationService::class.java)
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
                            is PayloadLauncher.Phone -> {
                                val phone = it.payload as? NotificationTriggerPayload.NumberPayload.PhonePayload
                                phone?.let {
                                    checkPhone(payloadLauncher.phone, phone.phone)
                                } ?: false
                            }
                            is PayloadLauncher.Sms -> {
                                val phone = it.payload as? NotificationTriggerPayload.NumberPayload.SmsPayload
                                phone?.let {
                                    checkPhone(payloadLauncher.phone, phone.phone)
                                } ?: false
                            }
                            else -> false
                        }
                    }.firstOrError()

    private fun checkPhone(phone: String, trigger: String) =
            phone.trim().replace(" ", "").replace("-", "").contains(trigger.trim()
                    .replace(" ", "").replace("-", ""))
}