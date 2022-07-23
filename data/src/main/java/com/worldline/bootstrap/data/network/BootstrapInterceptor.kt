package com.worldline.bootstrap.data.network

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class BootstrapInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val builder = request.newBuilder()

        /**
         * Intercept the requests and add a Bearer/Authorization token if required
         */

        return chain.proceed(builder.build())
    }
}
