package com.demont.ldap.domain.error

import com.demont.ldap.common.StatusCode
import javax.net.ssl.SSLPeerUnverifiedException

/**
 * App error class that serves as a container for different error types.
 */
sealed class AppError {

    /**
     * A simple error represented by a [String].
     *
     * @property message The message of the error
     * @property messageRes The string ID of the error
     */
    data class TextualError(
        val message: String? = null,
        val messageRes: Int? = null
    ) : AppError()

    /**
     * A common error represented by a [CommonErrorCode].
     *
     * @property code The [CommonErrorCode] for the error
     */
    data class CommonError(
        val code: CommonErrorCode
    ) : AppError() {

        @Suppress("StringTemplate")
        fun toPrintableCode() = "$ERROR_CODE_PREFIX-${code.code}"

        companion object {
            const val ERROR_CODE_PREFIX = 1
        }
    }

    /**
     * A network error represented by a [StatusCode].
     *
     * @property statusCode The [StatusCode] for the error
     */
    data class NetworkError(
        val statusCode: StatusCode,
    ) : AppError() {

        @Suppress("StringTemplate")
        fun toPrintableCode() = "$ERROR_CODE_PREFIX-${statusCode.code}"

        companion object {
            const val ERROR_CODE_PREFIX = 2
        }
    }
}

/**
 * Map an [Exception] to an [AppError].
 *
 * @return The associated [AppError.NetworkError] or [AppError.CommonError]
 */
fun Exception.mapToAppError(): AppError {
    return when (this) {
        is NetworkException -> AppError.NetworkError(code)
        else -> mapToCommonError()
    }
}

/**
 * Map an [Exception] to a [AppError.CommonError].
 *
 * @return the associated [AppError.CommonError]
 */
fun Exception.mapToCommonError(): AppError.CommonError {
    val code = when (this) {
        is NullPointerException -> CommonErrorCode.NullPointerException
        is IllegalStateException -> CommonErrorCode.IllegalStateException
        is IllegalArgumentException -> CommonErrorCode.IllegalArgumentException
        is ArrayIndexOutOfBoundsException -> CommonErrorCode.ArrayIndexOutOfBoundsException
        is SSLPeerUnverifiedException -> CommonErrorCode.SecurityCheck
        else -> CommonErrorCode.Unknown
    }

    return AppError.CommonError(code)
}
