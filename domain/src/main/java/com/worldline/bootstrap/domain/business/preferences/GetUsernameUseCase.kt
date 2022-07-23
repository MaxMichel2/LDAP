package com.worldline.bootstrap.domain.business.preferences

import com.worldline.bootstrap.domain.business.base.FlowUseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.PreferenceKey
import com.worldline.bootstrap.domain.model.Result
import com.worldline.bootstrap.domain.preferences.PreferenceRepository
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
