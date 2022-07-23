package com.worldline.bootstrap.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

//    @Provides
//    @AppFlavor
//    fun provideFlavor(): Flavor = Flavor.getFlavorFromCode(BuildConfig.FLAVOR)
//
//    @Provides
//    @AppVersionNameMinor
//    fun provideAppName(@AppFlavor flavor: Flavor): String {
//        return when (flavor) {
//            Flavor.PROD -> BuildConfig.VERSION_NAME_MINOR
//            else -> BuildConfig.VERSION_NAME
//        }
//    }
//
//    @Provides
//    @AppVersionName
//    fun provideAppVersionName() = BuildConfig.VERSION_NAME

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application
}
