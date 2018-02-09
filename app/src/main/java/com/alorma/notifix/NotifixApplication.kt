package com.alorma.notifix

import android.app.Application
import com.alorma.notifix.di.ApplicationComponent
import com.alorma.notifix.di.DaggerApplicationComponent

class NotifixApplication: Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.create()
    }
}