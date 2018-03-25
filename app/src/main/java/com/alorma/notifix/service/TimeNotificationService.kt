package com.alorma.notifix.service

import android.app.IntentService
import android.content.Intent
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.model.PayloadLauncher
import com.alorma.notifix.domain.usecase.GetTriggerUseCase
import com.alorma.notifix.domain.usecase.ShowNotificationsUseCase
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TimeNotificationService: IntentService("TimeNotificationService") {

    companion object {
        const val TRIGGER_ID = "trigger_id"
        const val TRIGGER_HOUR = "trigger_hour"
        const val TRIGGER_MINUTE = "trigger_minute"
    }

    @Inject
    lateinit var getTriggerUseCase: GetTriggerUseCase

    @Inject
    lateinit var showNotificationsUseCase: ShowNotificationsUseCase

    @Inject
    lateinit var logger: Logger

    override fun onHandleIntent(intent: Intent) {
        component inject this

        logger.d("Time trigger received")
        logger.d(intent.toString())


        if (intent.hasExtra(TimeNotificationService.TRIGGER_ID)) {
            val id = intent.getIntExtra(TimeNotificationService.TRIGGER_ID, -1)
            if (id != -1) {
                CompositeDisposable() += getTriggerUseCase.execute(id)
                        .filter { it.id != null }
                        .map { it.id }
                        .flatMapCompletable {
                            showNotificationsUseCase.execute(it)
                        }
                        .observeOnUI()
                        .subscribe({
                            logger.d("Complete!")
                        }, {
                            logger.d("Error: $it")
                        })
            }
        }
    }
}