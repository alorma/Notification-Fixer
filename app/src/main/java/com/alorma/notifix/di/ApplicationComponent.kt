package com.alorma.notifix.di;

import com.alorma.notifix.di.module.DataModule
import com.alorma.notifix.ui.features.notifications.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface ApplicationComponent {
    infix fun inject(mainActivity: MainActivity)
}