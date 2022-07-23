package com.worldline.bootstrap.domain.di

import com.worldline.bootstrap.domain.api.HelloWorldService
import com.worldline.bootstrap.domain.services.HelloWorldServiceImpl
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
