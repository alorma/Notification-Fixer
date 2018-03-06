package com.alorma.notifix.ui.features.trigger.di

import com.alorma.notifix.ui.features.trigger.number.ConfigureNumberTriggerFragment
import com.alorma.notifix.ui.features.trigger.time.ConfigureTimeTriggerFragment
import com.alorma.notifix.ui.features.trigger.zone.ConfigureZoneTriggerActivity
import dagger.Subcomponent

@Subcomponent(modules = [(CreateTriggerModule::class)])
interface CreateTriggerComponent {
    infix fun inject(fragment: ConfigureNumberTriggerFragment)
    infix fun inject(fragment: ConfigureTimeTriggerFragment)
    infix fun inject(fragment: ConfigureZoneTriggerActivity)
}