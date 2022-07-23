package com.demont.ldap.domain.business.base

import com.demont.ldap.domain.error.mapToAppError
import com.demont.ldap.domain.model.Result
import com.demont.ldap.domain.model.Result.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 * Handling an exception (emit [Result.Error] to the result) is the subclasses' responsibility.
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<Result<R>> {
        return execute(parameters)
            .catch { e -> emit(Error(Exception(e).mapToAppError())) }
            .flowOn(coroutineDispatcher)
    }

    abstract fun execute(parameters: P): Flow<Result<R>>
}
