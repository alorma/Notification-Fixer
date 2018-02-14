package com.alorma.notifix.data.framework

import android.app.Notification
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
            color = ContextCompat.getColor(context, appNotification.color)
            if (notificationConfig.fixed) {
                setOngoing(true)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    priority = NotificationManager.IMPORTANCE_HIGH
                } else {
                    setDefaults(Notification.DEFAULT_ALL)
                }
            }
        }.build()
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
        notifications.forEach { show(it) }
    }

    fun showOne(notification: AppNotification): Completable = Completable.fromCallable {
        show(notification)
    }

    private fun show(notification: AppNotification) {
        getNotificationManager().apply {
            createNotificationChannel(SHOW_CHANNEL, this)
            createNotification(notification, SHOW_CHANNEL, this)
        }
    }
}