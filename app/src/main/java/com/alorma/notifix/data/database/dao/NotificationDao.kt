package com.alorma.notifix.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.alorma.notifix.data.database.entity.NotificationEntity
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language

@Dao
interface NotificationDao {
    companion object {
        const val TABLE = "notifications"
    }

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE")
    fun getNotifications(): Flowable<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(map: NotificationEntity)
}