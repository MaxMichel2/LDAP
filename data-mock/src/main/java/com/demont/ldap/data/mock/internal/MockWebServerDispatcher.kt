package com.demont.ldap.data.mock.internal

import com.demont.ldap.common.StatusCode
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber

@Suppress("MagicNumber")
class MockWebServerDispatcher(
    private val assets: AssetProvider
) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path.orEmpty()

        return when {
            path == "/path" -> assets.createResponseFromAssets("example.json")
            URL_REGEX.matches(path) -> assets.createResponseFromHttpCodesAndAssetFiles(
                state = MockState.SUCCESS,
                endpointName = "path regex",
                fileName = "example.json",
                successCode = StatusCode.OK,
                errorCodes = listOf(
                    StatusCode.Forbidden,
                    StatusCode.UnprocessableEntity,
                    StatusCode.InternalServerError
                )
            )
            else -> {
                Timber.w(
                    "Mocked URL not handled for path (%s), returning empty response",
                    request.path
                )
                assets.createEmptyResponse()
            }
        }
    }

    companion object {
        private val URL_REGEX = Regex("/path/.*")
    }
}
