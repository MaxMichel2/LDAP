package com.worldline.bootstrap.app.conf

import com.worldline.bootstrap.data.di.ApiUrl
import com.worldline.bootstrap.data.di.AuthPinningCertificateDigest
import com.worldline.bootstrap.data.di.AuthenticationApiUrl
import com.worldline.bootstrap.data.di.DataModule
import com.worldline.bootstrap.data.di.PinningCertificateDigest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("FunctionMaxLength")
@Module
@InstallIn(SingletonComponent::class)
object EnvironmentModule {

    @Provides
    @ApiUrl
    fun provideApiUrl(): String = "https://www.worldline.com/api/"

    @Provides
    @AuthenticationApiUrl
    fun provideAuthenticationApiUrl(): String = "https://authentication.worldline.com/"

    /**
     * Provide pinning certificate digest for the [ApiUrl].
     *
     * Certificate pinning is handled by OkHttp in the [DataModule].
     */
    @Provides
    @PinningCertificateDigest
    fun providePinningCertificateDigest(): String = "sha256/TODO"

    /**
     * Provide pinning certificate digest for the [AuthenticationApiUrl].
     *
     * Certificate pinning is handled by OkHttp in the [DataModule].
     */
    @Provides
    @AuthPinningCertificateDigest
    fun provideAuthPinningCertificateDigest(): String = "sha256/TODO"
}
