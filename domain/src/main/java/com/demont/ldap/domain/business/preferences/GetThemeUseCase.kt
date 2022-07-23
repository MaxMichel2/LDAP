package com.demont.ldap.domain.business.preferences

import com.demont.ldap.domain.business.base.FlowUseCase
import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.domain.model.Result
import com.demont.ldap.domain.model.Theme
import com.demont.ldap.domain.preferences.PreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetThemeUseCase @Inject constructor(
    private val repository: PreferenceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Theme>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<Theme>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(repository.theme.first()))
        }
    }
}
