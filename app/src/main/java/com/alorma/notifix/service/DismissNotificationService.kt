package com.alorma.notifix.service

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import javax.inject.Inject

class DismissNotificationService : IntentService("DismissNotificationService") {

    companion object {
        const val NOTIFICATION_ID = "NOTIFICATION_ID"
    }

    @Inject
    lateinit var logger: Logger

    override fun onHandleIntent(intent: Intent) {
        component inject this

        intent.extras.getInt(NOTIFICATION_ID).let {
            if (it != 0) {
                getNotificationManager().cancel(it)
                logger.d("Remove notification")
            }
        }
    }

    private fun getNotificationManager(): NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}