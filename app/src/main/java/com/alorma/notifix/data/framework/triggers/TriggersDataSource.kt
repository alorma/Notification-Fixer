package com.alorma.notifix.data.framework.triggers

import com.alorma.notifix.domain.model.Trigger
import io.reactivex.Single

interface TriggersDataSource {
    fun getTriggers(): Single<List<Trigger>>
}