package com.worldline.bootstrap.app.conf

import android.content.Context
import com.worldline.bootstrap.data.di.ApiUrl
import com.worldline.bootstrap.data.di.AuthPinningCertificateDigest
import com.worldline.bootstrap.data.di.AuthenticationApiUrl
import com.worldline.bootstrap.data.di.PinningCertificateDigest
import com.worldline.bootstrap.data.mock.MockManager
import com.worldline.bootstrap.data.mock.MockServerUrl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Suppress("FunctionMaxLength")
@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {

    @Provides
    @MockServerUrl
    fun provideMockManager(@ApplicationContext context: Context) = MockManager().init(context)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Declarations {
        @Binds
        @ApiUrl
        abstract fun provideApiUrl(@MockServerUrl mockServerUrl: String): String

        @Binds
        @AuthenticationApiUrl
        abstract fun provideAuthenticationApiUrl(@MockServerUrl mockServerUrl: String): String
    }

    /**
     * Provide pinning certificate digest for the [ApiUrl].
     */
    @Provides
    @PinningCertificateDigest
    fun providePinningCertificateDigest(): String = "sha256/mock"

    /**
     * Provide pinning certificate digest for the [AuthenticationApiUrl].
     */
    @Provides
    @AuthPinningCertificateDigest
    fun provideAuthPinningCertificateDigest(): String = "sha256/mock"
}
