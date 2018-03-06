package com.alorma.notifix.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.alorma.notifix.data.database.dao.TriggersDao

/*

@Entity(tableName = NotificationDao.TABLE)
class NotificationEntity(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                         @ColumnInfo(name = "title") val title: String,
                         @ColumnInfo(name = "text") val text: String?,
                         @ColumnInfo(name = "color") val color: Int,
                         @ColumnInfo(name = NotificationDao.COL_CHECKED) val checked: Boolean)
 */

@Entity(tableName = TriggersDao.TABLE)
class TriggerEntity(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                    @ColumnInfo(name = "type") val type: String,
                    @ColumnInfo(name = "title") val payload: String)