package com.alorma.notifix.data.database.dao

import android.arch.persistence.room.*
import com.alorma.notifix.data.database.entity.NotificationEntity
import com.alorma.notifix.data.database.entity.TriggerEntity
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Language

@Dao
interface TriggersDao {
    companion object {
        const val TABLE = "triggers"
    }

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE WHERE ${TriggerEntity.FIELD_ID}=:id LIMIT 1")
    fun getTrigger(id: Int): Single<TriggerEntity>

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE")
    fun getTriggers(): Flowable<List<TriggerEntity>>

    @Language("RoomSql")
    @Query("SELECT * FROM $TABLE WHERE ${TriggerEntity.FIELD_TYPE}=:type ")
    fun getTriggers(type: String): Flowable<List<TriggerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(triggerEntity: TriggerEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(triggerEntity: TriggerEntity)
}