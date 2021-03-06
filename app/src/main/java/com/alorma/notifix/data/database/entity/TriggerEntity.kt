package com.alorma.notifix.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.alorma.notifix.data.database.dao.TriggersDao

@Entity(tableName = TriggersDao.TABLE)
class TriggerEntity(@PrimaryKey(autoGenerate = true) val id: Int? = null,
                    @ColumnInfo(name = FIELD_TYPE) val type: String,
                    @ColumnInfo(name = "payload") val payload: String) {
    companion object {
        const val FIELD_ID = "id"
        const val FIELD_TYPE = "type"
    }
}