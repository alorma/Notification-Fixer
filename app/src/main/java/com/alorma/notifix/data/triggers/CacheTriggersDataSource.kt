package com.alorma.notifix.data.triggers

import com.alorma.notifix.data.database.dao.TriggersDao
import com.alorma.notifix.data.database.entity.TriggerEntity
import com.alorma.notifix.data.triggers.setup.TimeTriggerSetup
import com.alorma.notifix.data.triggers.setup.ZoneTriggerSetup
import com.alorma.notifix.data.triggers.verify.TriggerVerifier
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.model.PayloadLauncher
import io.reactivex.Single
import javax.inject.Inject

class CacheTriggersDataSource @Inject constructor(
        private val triggersDao: TriggersDao,
        private val triggerVerifier: TriggerVerifier,
        private val timeTriggerSetup: TimeTriggerSetup,
        private val zoneTriggerSetup: ZoneTriggerSetup,
        private val triggersMapper: TriggersMapper) {


    fun save(trigger: NotificationTrigger): Single<Int> = Single.fromCallable {
        val entity: TriggerEntity = triggersMapper.map(trigger)
        triggersDao.insert(entity).toInt().apply {
            setupTrigger(trigger, this)
        }
    }

    private fun setupTrigger(trigger: NotificationTrigger, triggerId: Int) {
        when (trigger.payload) {
            is NotificationTriggerPayload.TimePayload -> timeTriggerSetup.setup(trigger.payload, triggerId)
            is NotificationTriggerPayload.ZonePayload -> zoneTriggerSetup.setup(trigger.payload, triggerId)
        }
    }

    fun get(id: Int): Single<NotificationTrigger> =
            triggersDao.getTrigger(id).map { triggersMapper.map(it) }

    fun get(payloadLauncher: PayloadLauncher): Single<NotificationTrigger> =
            triggersDao.getTriggers()
                    .flatMapIterable { it }
                    .map { triggersMapper.map(it) }
                    .filter { triggerVerifier.validator(it, payloadLauncher) }
                    .firstOrError()
}