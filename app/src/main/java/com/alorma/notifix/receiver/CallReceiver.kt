package com.alorma.notifix.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import com.alorma.notifix.domain.model.PayloadLauncher
import com.alorma.notifix.domain.usecase.GetTriggerUseCase
import com.alorma.notifix.domain.usecase.ShowNotificationsUseCase
import com.alorma.notifix.ui.utils.observeOnUI
import com.alorma.notifix.ui.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CallReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getTriggerUseCase: GetTriggerUseCase

    @Inject
    lateinit var useCase: ShowNotificationsUseCase

    @Inject
    lateinit var logger: Logger

    override fun onReceive(context: Context, intent: Intent) {

        component inject this

        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)?.let { onPhoneReceived(it) }
        } else if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            intent.getStringExtra("incoming_number")?.let {
                onPhoneReceived(it)
            }
        }
    }

    private fun onPhoneReceived(phone: String) {

        val launcher = PayloadLauncher.Phone(phone)

        CompositeDisposable() += getTriggerUseCase.execute(launcher)
                .observeOnUI()
                .subscribe({
                    logger.d("Trigger found $it")
                }, {
                    logger.d("Trigger not found: $it")
                })


        /*
        CompositeDisposable() += useCase.execute()
                .observeOnUI()
                .subscribe({
                    logger.d("Call success")
                }, {
                    logger.e(it.message ?: "Call error", it)
                })
        */
    }
}

