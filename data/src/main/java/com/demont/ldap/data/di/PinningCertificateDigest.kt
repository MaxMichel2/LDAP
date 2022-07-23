package com.demont.ldap.data.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PinningCertificateDigest

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AuthPinningCertificateDigest
