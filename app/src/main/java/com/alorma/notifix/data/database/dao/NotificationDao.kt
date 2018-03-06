package com.alorma.notifix.data.database.dao

import android.arch.persistence.room.*
import com.alorma.notifix.data.database.entity.NotificationEntity
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Language

@Dao
interface NotificationDao {
    companion object {
        const val TABLE = "notifications"
        const val COL_CHECKED = "checked"
    }

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE WHERE id=:id LIMIT 1")
    fun getNotification(id: Long): Single<NotificationEntity>

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE WHERE ${NotificationEntity.FIELD_TRIGGER}=:triggerId")
    fun getNotificationByTrigger(triggerId: Long): Single<List<NotificationEntity>>

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE")
    fun getNotifications(): Flowable<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notificationEntity: NotificationEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(notificationEntity: NotificationEntity)
}