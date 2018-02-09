package com.alorma.notifix.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    fun getContext(): Context = context
}