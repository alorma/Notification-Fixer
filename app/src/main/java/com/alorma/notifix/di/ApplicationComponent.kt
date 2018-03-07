package com.alorma.notifix.di;

import com.alorma.notifix.background.notifications.NotificationsBootBroadcast
import com.alorma.notifix.di.module.ApplicationModule
import com.alorma.notifix.di.module.DataModule
import com.alorma.notifix.receiver.CallReceiver
import com.alorma.notifix.receiver.SmsReceiver
import com.alorma.notifix.service.DismissNotificationService
import com.alorma.notifix.service.TimeNotificationService
import com.alorma.notifix.ui.features.create.AddNotificationActivity
import com.alorma.notifix.ui.features.notifications.MainActivity
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerComponent
import com.alorma.notifix.ui.features.trigger.di.CreateTriggerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class])
interface ApplicationComponent {
    infix fun inject(activity: MainActivity)
    infix fun inject(activity: AddNotificationActivity)
    infix fun inject(bootBroadcast: NotificationsBootBroadcast)

    infix fun add(module: CreateTriggerModule): CreateTriggerComponent

    infix fun inject(callReceiver: CallReceiver)
    infix fun inject(smsReceiver: SmsReceiver)
    infix fun inject(timeNotificationService: TimeNotificationService)
    infix fun inject(dismissNotificationService: DismissNotificationService)
}