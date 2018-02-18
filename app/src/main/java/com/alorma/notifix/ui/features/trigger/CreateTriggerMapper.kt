package com.alorma.notifix.ui.features.trigger

import com.alorma.notifix.domain.model.Trigger
import javax.inject.Inject

class CreateTriggerMapper @Inject constructor() {
    fun mapTriggers(it: List<Trigger>) = TriggersSuccess(it)
}