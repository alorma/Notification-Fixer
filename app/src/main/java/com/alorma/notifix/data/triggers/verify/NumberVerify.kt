package com.alorma.notifix.data.triggers.verify

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.model.PayloadLauncher
import javax.inject.Inject

class NumberVerify @Inject constructor() : TriggerVerify() {
    override fun verify(it: NotificationTrigger, payloadLauncher: PayloadLauncher): Boolean {
        return (it.payload as? NotificationTriggerPayload.NumberPayload.SmsPayload)?.let {
            (payloadLauncher as? PayloadLauncher.Sms)?.let {
                verifyPhone(payloadLauncher.phone, it.phone)
            } ?: false
        } ?: (it.payload as? NotificationTriggerPayload.NumberPayload.PhonePayload)?.let {
            (payloadLauncher as? PayloadLauncher.Phone)?.let {
                verifyPhone(payloadLauncher.phone, it.phone)
            } ?: false
        } ?: false
    }

    private fun verifyPhone(payloadPhone: String, phone: String) =
            checkPhone(payloadPhone, phone) || checkPhone(phone, payloadPhone)

    private fun checkPhone(phone: String, trigger: String) =
            phone.trim().replace(" ", "").replace("-", "").contains(trigger.trim()
                    .replace(" ", "").replace("-", ""))

}