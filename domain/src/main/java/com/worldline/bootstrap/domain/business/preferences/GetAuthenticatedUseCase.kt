package com.worldline.bootstrap.domain.business.preferences

import com.worldline.bootstrap.domain.business.base.UseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.PreferenceKey
import com.worldline.bootstrap.domain.preferences.PreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first

class GetAuthenticatedUseCase @Inject constructor(
    private val repository: PreferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Boolean>(dispatcher) {

    override suspend fun execute(parameters: Unit): Boolean {
        return repository.getPreference<Boolean?>(PreferenceKey.AUTHENTICATED).first() ?: false
    }
}
