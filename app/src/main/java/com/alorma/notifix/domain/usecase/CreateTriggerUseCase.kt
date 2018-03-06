package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.NotificationTriggerPayload
import com.alorma.notifix.domain.repository.TriggersRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateTriggerUseCase @Inject constructor(private val triggersRepository: TriggersRepository) {

    fun execute(payload: NotificationTriggerPayload): Single<Long>
            = triggersRepository.createTrigger(NotificationTrigger(payload))
}