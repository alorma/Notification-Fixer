package com.alorma.notifix.domain.usecase

import com.alorma.notifix.domain.model.Trigger
import com.alorma.notifix.domain.repository.TriggersRepository
import io.reactivex.Single
import javax.inject.Inject

class ObtainTriggersUseCase @Inject constructor(private val triggersRepository: TriggersRepository) {
    fun execute(): Single<List<Trigger>> = triggersRepository.getTriggers()
}