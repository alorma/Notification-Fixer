package com.alorma.notifix.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.model.PayloadLauncher
import com.alorma.notifix.domain.usecase.ShowNotificationsUseCase
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SmsReceiver: BroadcastReceiver() {

    @Inject
    lateinit var useCase: ShowNotificationsUseCase

    @Inject
    lateinit var logger: Logger


    override fun onReceive(context: Context, intent: Intent) {
        component inject this
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Telephony.Sms.Intents.getMessagesFromIntent(intent)
                    .map { it.displayOriginatingAddress }
                    .forEach { onPhoneReceived(it) }
        }
    }

    private fun onPhoneReceived(phone: String) {

    }
}