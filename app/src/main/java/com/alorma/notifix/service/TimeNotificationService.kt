package com.alorma.notifix.service

import android.app.IntentService
import android.content.Intent
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import javax.inject.Inject

class TimeNotificationService: IntentService("TimeNotificationService") {

    @Inject
    lateinit var logger: Logger

    override fun onHandleIntent(intent: Intent) {
        component inject this

        logger.d("Time trigger received")
    }
}