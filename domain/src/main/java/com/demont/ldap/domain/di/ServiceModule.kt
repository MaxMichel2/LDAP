package com.demont.ldap.domain.di

import com.demont.ldap.domain.api.HelloWorldService
import com.demont.ldap.domain.services.HelloWorldServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideHelloWorldService(helloWorldService: HelloWorldServiceImpl): HelloWorldService
}
