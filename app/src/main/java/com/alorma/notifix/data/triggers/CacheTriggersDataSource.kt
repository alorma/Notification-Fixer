package com.alorma.notifix.data.triggers

import com.alorma.notifix.data.database.dao.TriggersDao
import com.alorma.notifix.data.database.entity.TriggerEntity
import com.alorma.notifix.domain.model.NotificationTrigger
import io.reactivex.Single
import javax.inject.Inject

class CacheTriggersDataSource @Inject constructor(private val triggersDao: TriggersDao,
                                                  private val triggersMapper: TriggersMapper) {

    fun save(trigger: NotificationTrigger): Single<Long> = Single.fromCallable {
        val entity: TriggerEntity = triggersMapper.map(trigger)
        triggersDao.insert(entity)
    }
}