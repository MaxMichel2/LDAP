package com.worldline.bootstrap.data.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiUrl

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AuthenticationApiUrl
