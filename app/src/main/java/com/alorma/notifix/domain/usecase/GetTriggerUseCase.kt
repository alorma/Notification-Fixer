package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.NotificationTrigger
import com.alorma.notifix.domain.model.PayloadLauncher
import com.alorma.notifix.domain.repository.TriggersRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTriggerUseCase @Inject constructor(private val triggersRepository: TriggersRepository) {

    fun execute(payloadLauncher: PayloadLauncher): Single<NotificationTrigger> =
            triggersRepository.getByPayload(payloadLauncher)

}