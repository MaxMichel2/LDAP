package com.demont.ldap.domain.business.preferences

import com.demont.ldap.domain.business.base.FlowUseCase
import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.domain.model.PreferenceKey
import com.demont.ldap.domain.model.Result
import com.demont.ldap.domain.preferences.PreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsernameUseCase @Inject constructor(
    private val repository: PreferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, String?>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<String?>> {
        return flow {
            emit(Result.Loading)
            delay(1500)
            repository.getPreference<String?>(PreferenceKey.USERNAME).collect { username ->
                emit(Result.Success(username))
            }
        }
    }
}
