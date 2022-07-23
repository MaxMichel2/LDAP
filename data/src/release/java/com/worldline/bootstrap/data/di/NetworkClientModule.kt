package com.worldline.bootstrap.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.worldline.bootstrap.data.network.BootstrapInterceptor
import com.worldline.bootstrap.data.network.NetworkErrorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
class NetworkClientModule {

    /**
     * Provides an [OkHttpClient] with Hilt dependency injection.
     *
     * @param context The [ApplicationContext] provided by Hilt
     * @param interceptor A custom [Interceptor] implementation
     */
    @Provides
    fun provideHttpClient(
        @ApplicationContext context: Context,
        interceptor: BootstrapInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_S, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_S, TimeUnit.SECONDS)
            .addInterceptor(NetworkErrorInterceptor())
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .addInterceptor(interceptor)

        return builder.build()
    }

    companion object {
        private const val TIMEOUT_IN_S: Long = 15
    }
}
