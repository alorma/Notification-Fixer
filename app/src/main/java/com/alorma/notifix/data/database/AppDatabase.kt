package com.alorma.notifix.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.alorma.notifix.data.database.dao.NotificationDao
import com.alorma.notifix.data.database.entity.NotificationEntity

@Database(entities = [(NotificationEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val NAME: String = "database"
    }

    abstract fun notificationDao(): NotificationDao
}