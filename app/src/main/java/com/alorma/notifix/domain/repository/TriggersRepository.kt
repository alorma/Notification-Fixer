package com.alorma.notifix.domain.repository

import com.alorma.notifix.data.framework.triggers.DefaultTriggersDataSource
import com.alorma.notifix.domain.model.Trigger
import io.reactivex.Single
import javax.inject.Inject

class TriggersRepository @Inject constructor(private val defaultTriggersDataSource: DefaultTriggersDataSource) {

    fun getTriggers(): Single<List<Trigger>> = defaultTriggersDataSource.getTriggers()

}