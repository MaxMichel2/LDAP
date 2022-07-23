package com.worldline.bootstrap.domain.business.preferences

import com.worldline.bootstrap.domain.business.base.UseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.PreferenceKey
import com.worldline.bootstrap.domain.preferences.PreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class LogoutUseCase @Inject constructor(
    private val repository: PreferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {

    override suspend fun execute(parameters: Unit) {
        repository.updatePreference(PreferenceKey.AUTHENTICATED, false)
    }
}
