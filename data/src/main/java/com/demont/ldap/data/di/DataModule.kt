package com.demont.ldap.data.di

import androidx.core.net.toUri
import com.demont.ldap.data.network.BootstrapApi
import com.demont.ldap.data.preferences.PreferenceRepositoryImpl
import com.demont.ldap.data.repositories.HelloWorldRepositoryImpl
import com.demont.ldap.domain.preferences.PreferenceRepository
import com.demont.ldap.domain.repositories.HelloWorldRepository
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Declarations {
        @Binds
        abstract fun provideHelloWorldRepository(repository: HelloWorldRepositoryImpl): HelloWorldRepository

        @Binds
        @Singleton
        abstract fun providePreferenceRepository(repository: PreferenceRepositoryImpl): PreferenceRepository
    }

    /**
     * Provides an API instance with Hilt dependency injection which users can use to request
     * specific endpoints.
     *
     * @param moshi The [Moshi] instance provided by the [NetworkModule]
     * @param client The [OkHttpClient] instance provided by the [NetworkClientModule]
     * @param url The [ApiUrl] instance provided by the EnvironmentModule in **`../../app/conf`**
     * @param digest The [PinningCertificateDigest] instance provided by the EnvironmentModule in
     * **`../../app/conf`**
     * @return A [BootstrapApi] instance
     */
    @Provides
    fun provideBootstrapApi(
        moshi: Moshi,
        client: OkHttpClient,
        @ApiUrl url: String,
        @PinningCertificateDigest digest: String,
        // Add an OkHttp3 Authenticator here if needed
    ): BootstrapApi {
        val builder = client.newBuilder()

        val host: String = url.toUri().host.orEmpty()
        builder.certificatePinner(
            CertificatePinner.Builder().add(host, digest).build()
        )

        val httpClient = builder
            // .authenticator(<Your Authenticator>)
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
            .create(BootstrapApi::class.java)
    }
}
