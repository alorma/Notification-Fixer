package com.alorma.notifix.data.framework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.alorma.notifix.R
import com.alorma.notifix.domain.model.AppNotification
import io.reactivex.Completable
import javax.inject.Inject

class DisplayNotificationDataSource @Inject constructor(private val context: Context) {
    companion object {
        private val PREVIEW_CHANNEL = NotificationConfig("channel_preview", "Preview", "Preview channel", false)
        private val SHOW_CHANNEL = NotificationConfig("channel_show", "Fixed", "Fixed channel", true)
    }

    fun showPreview(notification: AppNotification): Completable = Completable.fromCallable {
        getNotificationManager().apply {
            createNotificationChannel(PREVIEW_CHANNEL, this)
            createNotification(notification, PREVIEW_CHANNEL, this)
        }
    }

    private fun getNotificationManager(): NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun createNotification(appNotification: AppNotification,
                                   notificationConfig: NotificationConfig,
                                   notificationManager: NotificationManager) {
        val notification = NotificationCompat.Builder(context, notificationConfig.channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(appNotification.title)
            setContentText(appNotification.text)

            if (appNotification.color != 0) {
                color = ContextCompat.getColor(context, appNotification.color)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                priority = if (notificationConfig.fixed) {
                    NotificationCompat.PRIORITY_LOW
                } else {
                    NotificationCompat.PRIORITY_HIGH
                }
            }
        }.build().apply {
            if (notificationConfig.fixed) {
                flags = NotificationCompat.FLAG_NO_CLEAR
            }
        }

        notificationManager.notify(appNotification.id, notification)
    }

    private fun createNotificationChannel(notificationConfig: NotificationConfig,
                                          notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationConfig.channelId, notificationConfig.channelName,
                    NotificationManager.IMPORTANCE_HIGH).apply {
                description = notificationConfig.channelDescription
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun show(notifications: List<AppNotification>): Completable = Completable.fromCallable {
        val activeNotifications = getCurrentNotifications()
        notifications.filter { !activeNotifications.contains(it.id) }.forEach {
            if (it.triggerId == null) {
                show(it)
            } else {
                configureTrigger(it)
            }
        }
    }

    fun showFromTrigger(notifications: List<AppNotification>): Completable = Completable.fromCallable {
        val activeNotifications = getCurrentNotifications()
        notifications.filter { !activeNotifications.contains(it.id) }.forEach {
            show(it)
        }
    }

    fun showOneFromTrigger(notification: AppNotification): Completable = Completable.fromCallable {
        val activeNotifications = getCurrentNotifications()
        if (!activeNotifications.contains(notification.id)) {
            show(notification)
        }
    }

    private fun getCurrentNotifications(): List<Int> =
            getNotificationManager().activeNotifications.toList().map { it.id }

    fun showOne(notification: AppNotification): Completable = Completable.fromCallable {
        if (!getCurrentNotifications().contains(notification.id)) {
            show(notification)
        }
    }

    private fun show(notification: AppNotification) {
        if (notification.checked == true) {
            getNotificationManager().apply {
                createNotificationChannel(SHOW_CHANNEL, this)
                createNotification(notification, SHOW_CHANNEL, this)
            }
        }
    }

    fun dismiss(notificationId: Int): Completable = Completable.fromCallable {
        getNotificationManager().cancel(notificationId)
    }

    fun prepareTrigger(it: AppNotification): Completable = Completable.fromCallable { configureTrigger(it) }

    private fun configureTrigger(it: AppNotification) {

    }
}