package com.alorma.notifix.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.alorma.notifix.data.database.dao.NotificationDao

@Entity(tableName = NotificationDao.TABLE)
class NotificationEntity(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                         @ColumnInfo(name = "title") val title: String,
                         @ColumnInfo(name = "text") val text: String?,
                         @ColumnInfo(name = "color") val color: Int,
                         @ColumnInfo(name = FIELD_TRIGGER) val trigger: Int?,
                         @ColumnInfo(name = NotificationDao.COL_CHECKED) val checked: Boolean) {
    companion object {
        const val FIELD_TRIGGER = "trigger"
    }
}