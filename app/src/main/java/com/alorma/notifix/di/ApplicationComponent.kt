package com.alorma.notifix.di;

import com.alorma.notifix.di.module.ApplicationModule
import com.alorma.notifix.di.module.DataModule
import com.alorma.notifix.ui.features.create.AddNotificationActivity
import com.alorma.notifix.ui.features.notifications.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class])
interface ApplicationComponent {
    infix fun inject(activity: MainActivity)
    infix fun inject(activity: AddNotificationActivity)
}