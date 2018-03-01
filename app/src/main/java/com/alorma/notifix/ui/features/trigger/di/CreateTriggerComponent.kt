package com.alorma.notifix.ui.features.trigger.di

import com.alorma.notifix.ui.features.trigger.ConfigureNumberTriggerActivity
import dagger.Subcomponent

@Subcomponent(modules = [(CreateTriggerModule::class)])
interface CreateTriggerComponent {

    //infix fun inject(activity: ConfigurePhoneTriggerActivity)
    //infix fun inject(activity: ConfigureSmsTriggerActivity)

    infix fun inject(activity: ConfigureNumberTriggerActivity)

}