package com.demont.ldap.app.conf

import android.content.Context
import com.demont.ldap.data.di.ApiUrl
import com.demont.ldap.data.di.AuthPinningCertificateDigest
import com.demont.ldap.data.di.AuthenticationApiUrl
import com.demont.ldap.data.di.PinningCertificateDigest
import com.demont.ldap.data.mock.MockManager
import com.demont.ldap.data.mock.MockServerUrl
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
