package com.alorma.notifix.domain.repository

import com.alorma.notifix.data.triggers.CacheTriggersDataSource
import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.ui.utils.subscribeOnIO
import io.reactivex.Single
import javax.inject.Inject

class TriggersRepository @Inject constructor(private val cacheTriggersDataSource: CacheTriggersDataSource) {

    fun createTrigger(trigger: NotificationTrigger): Single<Long> = cacheTriggersDataSource.save(trigger).subscribeOnIO()
}