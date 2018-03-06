package com.alorma.notifix.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.alorma.notifix.data.database.dao.NotificationDao
import com.alorma.notifix.data.database.dao.TriggersDao
import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.data.database.entity.TriggerEntity

@Database(entities = [(NotificationEntity::class), (TriggerEntity::class)],
        version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val NAME: String = "database"
    }

    abstract fun notificationDao(): NotificationDao

    abstract fun triggersDao(): TriggersDao
}