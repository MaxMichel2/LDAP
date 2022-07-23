package com.worldline.bootstrap.domain.model

import androidx.lifecycle.MutableLiveData
import com.worldline.bootstrap.domain.error.AppError

/**
 * A generic class that holds a value with its loading status.
 *
 * @param R The return type of the [Result]
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: AppError) : Result<Nothing>()
    object Loading : Result<Nothing>()

    fun isSuccessful() = this is Success
    fun hasFailed() = this is Error
    fun isLoading() = this is Loading

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
            Loading -> "Loading"
        }
    }
}

/**
 * [Success.data][Result.Success.data] if [Result] is of type [Success][Result.Success]
 */
fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (this is Result.Success<T>) {
        block(data)
    }

    return this
}

inline fun <T> Result<T>.onError(block: (AppError) -> Unit): Result<T> {
    if (this is Result.Error) {
        block(error)
    }

    return this
}

inline fun <T> Result<T>.whenFinished(block: () -> Unit): Result<T> {
    block()
    return this
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

/**
 * Updates value of [liveData] if [Result] is of query [Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is Result.Success) {
        liveData.value = data
    }
}
