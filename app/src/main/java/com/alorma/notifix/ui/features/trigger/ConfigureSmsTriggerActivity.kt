package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.R

class ConfigureSmsTriggerActivity : ConfigureNumberTriggerActivity() {
    override fun getScreenTitle(): Int = R.string.title_activity_configure_sms_trigger
    override fun triggerType(): TriggerType = Sms
}