package com.alorma.notifix.data.triggers.setup

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.service.TimeNotificationService
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeTriggerSetup @Inject constructor(private val context: Context) :
        TriggerSetup<NotificationTriggerPayload.TimePayload>() {

    companion object {
        private const val REQUEST = 112
    }

    override fun setup(payload: NotificationTriggerPayload.TimePayload, triggerId: Int) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val millisRepeat = TimeUnit.SECONDS.toMillis(30)

        val intent = Intent(context, TimeNotificationService::class.java).apply {
            putExtras(Bundle().apply {
                putInt(TimeNotificationService.TRIGGER_ID, triggerId)
                putInt(TimeNotificationService.TRIGGER_HOUR, payload.hour)
                putInt(TimeNotificationService.TRIGGER_MINUTE, payload.minute)
            })
        }
        val pending = PendingIntent.getService(context, REQUEST, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val time = Calendar.getInstance().apply {
            set(Calendar.HOUR, payload.hour)
            set(Calendar.MINUTE, payload.minute)
        }.timeInMillis

        am.setRepeating(AlarmManager.RTC_WAKEUP, time, millisRepeat, pending)
    }
}