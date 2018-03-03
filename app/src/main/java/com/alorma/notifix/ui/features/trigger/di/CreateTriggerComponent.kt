package com.alorma.notifix.ui.features.trigger.di

import com.alorma.notifix.ui.features.trigger.number.ConfigureNumberNumberTriggerFragment
import com.alorma.notifix.ui.features.trigger.time.ConfigureTimeTriggerFragment
import dagger.Subcomponent

@Subcomponent(modules = [(CreateTriggerModule::class)])
interface CreateTriggerComponent {
    infix fun inject(fragment: ConfigureNumberNumberTriggerFragment)
    infix fun inject(fragment: ConfigureTimeTriggerFragment)
}