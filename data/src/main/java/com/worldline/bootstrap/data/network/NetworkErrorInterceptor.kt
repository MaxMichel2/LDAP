package com.worldline.bootstrap.data.network

import com.worldline.bootstrap.common.StatusCode
import com.worldline.bootstrap.domain.error.NetworkException
import java.io.IOException
import java.net.UnknownHostException
import java.nio.charset.Charset
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import okio.GzipSource
import timber.log.Timber

class NetworkErrorInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response: Response
        try {
            response = chain.proceed(request)

            if (!response.isSuccessful) {
                throw NetworkException(StatusCode.from(response.code))
            }
        } catch (exception: UnknownHostException) {
            Timber.e(exception)
            throw NetworkException(StatusCode.NoNetwork)
        }

        return response
    }

    private fun responseBody(response: Response): String? {
        val responseBody = response.body ?: return null
        val contentLength = responseBody.contentLength()

        return if (contentLength == 0L) {
            null
        } else {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer
            val headers = response.headers

            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                var gzippedResponseBody: GzipSource? = null
                try {
                    gzippedResponseBody = GzipSource(buffer.clone())
                    buffer = okio.Buffer()
                    buffer.writeAll(gzippedResponseBody)
                } finally {
                    gzippedResponseBody?.close()
                }
            }

            val charset: Charset = responseBody.contentType()?.charset(UTF8) ?: UTF8
            buffer.clone().readString(charset)
        }
    }

    private companion object {
        val UTF8: Charset = Charset.forName("UTF-8")
    }
}
