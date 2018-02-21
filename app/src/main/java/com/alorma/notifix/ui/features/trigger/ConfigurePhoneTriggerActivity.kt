package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.R

class ConfigurePhoneTriggerActivity : ConfigureNumberTriggerActivity()  {
    override fun getScreenTitle(): Int = R.string.title_activity_configure_phone_trigger
    override fun triggerType(): TriggerType = Phone
}