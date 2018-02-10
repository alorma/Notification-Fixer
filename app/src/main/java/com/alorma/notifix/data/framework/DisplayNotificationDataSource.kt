package com.alorma.notifix.data.framework

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.alorma.notifix.R
import com.alorma.notifix.domain.model.AppNotification
import io.reactivex.Completable
import javax.inject.Inject

class DisplayNotificationDataSource @Inject constructor(private val context: Context) {
    companion object {
        private const val CHANNEL_ID = "channel_preview"
        private const val CHANNEL_NAME = "Preview"
        private const val CHANNEL_DESCRIPTION = "Preview channel"
    }

    fun showPreview(appNotification: AppNotification): Completable = Completable.fromCallable {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChanel(notificationManager)
        createNotification(appNotification, notificationManager)
    }

    private fun createNotification(appNotification: AppNotification, notificationManager: NotificationManager) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(appNotification.title)
            setContentText(appNotification.text)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                priority = NotificationManager.IMPORTANCE_HIGH
            } else {
                setDefaults(Notification.DEFAULT_ALL)
            }
        }.build()
        notificationManager.notify(appNotification.id, notification)
    }

    private fun createNotificationChanel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                description = CHANNEL_DESCRIPTION
                setShowBadge(false)
            }

            notificationManager.createNotificationChannel(channel)
        }
    }
}