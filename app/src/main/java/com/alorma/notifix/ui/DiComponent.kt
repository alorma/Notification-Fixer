package com.alorma.notifix.ui;

import com.alorma.notifix.ui.features.notifications.MainActivity
import dagger.Component

@Component
interface DiComponent {
    companion object {
        val component = DaggerDiComponent.create()
    }

    infix fun inject(activity: MainActivity)
}