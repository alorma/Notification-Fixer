package com.alorma.notifix.ui.features.trigger.di

import com.alorma.notifix.ui.features.trigger.number.ConfigureNumberTriggerFragment
import dagger.Subcomponent

@Subcomponent(modules = [(CreateTriggerModule::class)])
interface CreateTriggerComponent {
    infix fun inject(fragment: ConfigureNumberTriggerFragment)
}