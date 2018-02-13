package com.alorma.notifix.di.module

import android.arch.persistence.room.BuildConfig
import android.arch.persistence.room.Room
import android.content.Context
import com.alorma.notifix.data.AndroidLogger
import com.alorma.notifix.data.Logger
import com.alorma.notifix.data.database.AppDatabase
import com.alorma.notifix.data.database.dao.NotificationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, AppDatabase.NAME)
                .apply {
                    if (BuildConfig.DEBUG) fallbackToDestructiveMigration()
                }
                .build()
    }

    @Provides
    @Singleton
    fun getNotificationDao(appDatabase: AppDatabase): NotificationDao = appDatabase.notificationDao()

    @Provides
    @Singleton
    fun getLogger(): Logger = AndroidLogger()
}