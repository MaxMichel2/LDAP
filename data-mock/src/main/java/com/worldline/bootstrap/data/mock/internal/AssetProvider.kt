package com.worldline.bootstrap.data.mock.internal

import android.content.res.AssetManager
import com.worldline.bootstrap.common.StatusCode
import java.util.Calendar
import java.util.Scanner
import java.util.concurrent.TimeUnit
import okhttp3.mockwebserver.MockResponse
import timber.log.Timber

@Suppress(
    "MagicNumber",
    "ComplexMethod",
    "NestedBlockDepth",
    "FunctionMaxLength",
    "CommentOverPrivateFunction"
)
class AssetProvider(
    private val assetManager: AssetManager
) {

    /**
     * Create a [MockResponse] object from a file.
     *
     * @param fileName The name of the file from which to create a [MockResponse]. This must contain
     * the file extension (.json, .txt, etc.)
     * @param statusCode The [StatusCode] to apply to said response. The default value is
     * [StatusCode.OK]
     * @param delayInMs The delay with which to return the body of the [MockResponse]
     */
    fun createResponseFromAssets(
        fileName: String,
        statusCode: StatusCode = StatusCode.OK,
        delayInMs: Long = DEFAULT_DELAY_IN_MS
    ): MockResponse {
        val inputStream = assetManager.open(fileName)

        val s = Scanner(inputStream, Charsets.UTF_8.name()).useDelimiter("\\A")
        val result = if (s.hasNext()) s.next() else ""

        return MockResponse()
            .setBody(result)
            .setResponseCode(statusCode.code)
            .setBodyDelay(delayInMs, TimeUnit.MILLISECONDS)
    }

    /**
     * Create a [MockResponse] object from an input.
     *
     * @param content The content from which to create a [MockResponse].
     * @param statusCode The [StatusCode] to apply to said response. The default value is
     * [StatusCode.OK]
     * @param delayInMs The delay with which to return the body of the [MockResponse]
     */
    fun createResponse(
        content: String,
        statusCode: StatusCode = StatusCode.OK,
        delayInMs: Long = DEFAULT_DELAY_IN_MS
    ): MockResponse =
        MockResponse()
            .setBody(content)
            .setResponseCode(statusCode.code)
            .setBodyDelay(delayInMs, TimeUnit.MILLISECONDS)

    /**
     * Create an empty [MockResponse].
     *
     * @param statusCode The [StatusCode] to apply to said response. The default value is
     * [StatusCode.OK]
     * @param delayInMs The delay with which to return the body of the [MockResponse]
     */
    fun createEmptyResponse(
        statusCode: StatusCode = StatusCode.OK,
        delayInMs: Long = DEFAULT_DELAY_IN_MS
    ): MockResponse =
        MockResponse()
            .setBody("")
            .setResponseCode(statusCode.code)
            .setHeadersDelay(delayInMs, TimeUnit.MILLISECONDS)

    /**
     * Create a [MockResponse] from http codes and asset files.
     *
     * @param state The [MockState] to use to create the [MockResponse]
     * @param endpointName The name of the endpoint for which the response is created
     * @param fileName The name of the file from which to create a [MockResponse]. This must contain
     * the file extension (.json, .txt, etc.)
     * @param successCode The [StatusCode] to apply to said response if [state] can be successful.
     * @param errorCodes The [StatusCode]s to apply to said response if [state] can be unsuccessful.
     */
    fun createResponseFromHttpCodesAndAssetFiles(
        state: MockState = MockState.SUCCESS,
        endpointName: String,
        fileName: String? = null,
        successCode: StatusCode,
        errorCodes: List<StatusCode>
    ): MockResponse {
        return if (state == MockState.SUCCESS) {
            Timber.d("state == SUCCESS -> $endpointName success")
            createResponseFromAssetsOrEmpty(fileName, successCode)
        } else if (state == MockState.FAILURE) {
            Timber.d("state == FAILURE -> $endpointName fail")

            if (errorCodes.size > 1) {
                val currentTime = Calendar.getInstance()
                val modMinutes = currentTime.get(Calendar.MINUTE) % DEFAULT_VALUE_FOR_MODULO
                Timber.d("modMinutes = $modMinutes")
                when (errorCodes.size) {
                    2 -> if (modMinutes == 0) { // ex: at 08:30
                        createEmptyResponse(statusCode = errorCodes[0])
                    } else { // ex: at 08:31
                        createEmptyResponse(statusCode = errorCodes[1])
                    }
                    3, 4 -> {
                        val divSeconds =
                            currentTime.get(Calendar.SECOND) / DEFAULT_VALUE_FOR_DIV_SECONDS
                        Timber.d("divSeconds = $divSeconds")

                        when {
                            modMinutes == 0 && divSeconds == 0 -> { // ex: at 08:30:00
                                createEmptyResponse(statusCode = errorCodes[0])
                            }
                            modMinutes == 0 && divSeconds == 1 -> { // ex: at 08:30:01
                                createEmptyResponse(statusCode = errorCodes[1])
                            }
                            modMinutes == 1 && divSeconds == 0 -> { // ex: at 08:31:00
                                createEmptyResponse(statusCode = errorCodes[2])
                            }
                            else -> { // ex: at 08:31:01
                                if (errorCodes.size == 3) {
                                    createEmptyResponse(statusCode = errorCodes[2])
                                } else {
                                    createEmptyResponse(statusCode = errorCodes[3])
                                }
                            }
                        }
                    }
                    else -> createEmptyResponse(statusCode = errorCodes[0])
                }
            } else {
                createEmptyResponse(statusCode = errorCodes[0])
            }
        } else {
            Timber.d("state != SUCCESS && != FAILURE -> $endpointName fail/success")
            val currentTime = Calendar.getInstance()
            val modMinutes = currentTime.get(Calendar.MINUTE) % DEFAULT_VALUE_FOR_MODULO
            Timber.d("modMinutes = $modMinutes")

            if (modMinutes == 0) { // ex: at 08:30
                createResponseFromAssetsOrEmpty(fileName, successCode)
            } else { // ex: at 08:31
                createEmptyResponse(statusCode = errorCodes[0])
            }
        }
    }

    /**
     * Private function to create a [MockResponse] based on whether the [fileName] is null
     *
     * @param fileName The name of the file from which to create the response or null
     * @param successCode The success [StatusCode] to apply to the [MockResponse]
     */
    private fun createResponseFromAssetsOrEmpty(
        fileName: String?,
        successCode: StatusCode
    ): MockResponse {
        return if (fileName != null) {
            createResponseFromAssets(fileName, successCode)
        } else {
            createEmptyResponse(successCode)
        }
    }

    companion object {
        private const val DEFAULT_DELAY_IN_MS = 1_000L
    }
}
