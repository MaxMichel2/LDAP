package com.demont.ldap.domain.business.preferences

import com.demont.ldap.domain.business.base.UseCase
import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.domain.model.PreferenceKey
import com.demont.ldap.domain.preferences.PreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class LoginUseCase @Inject constructor(
    private val repository: PreferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<String?, Unit>(dispatcher) {

    override suspend fun execute(parameters: String?) {
        repository.updatePreference(PreferenceKey.AUTHENTICATED, true)
        repository.updatePreference(PreferenceKey.USERNAME, parameters)
    }
}
