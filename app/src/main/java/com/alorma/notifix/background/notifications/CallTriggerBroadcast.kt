package com.alorma.notifix.background.notifications

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.alorma.notifix.NotifixApplication.Companion.component
import com.alorma.notifix.data.Logger
import javax.inject.Inject


class CallTriggerBroadcast : BroadcastReceiver() {

    @Inject
    lateinit var logger: Logger


    override fun onReceive(context: Context, intent: Intent?) {
        component inject this

        if (intent?.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            val phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)

            logger.d("Out: $phoneNumber")
        } else {
            val tm = context.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager

            if (tm.callState == TelephonyManager.CALL_STATE_RINGING) {

            }
        }
    }
}