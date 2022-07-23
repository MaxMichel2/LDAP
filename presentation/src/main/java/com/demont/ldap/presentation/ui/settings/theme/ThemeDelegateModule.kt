package com.demont.ldap.presentation.ui.settings.theme

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemeDelegateModule {

    @Binds
    abstract fun provideThemedDelegate(impl: ThemeDelegateImpl): ThemeDelegate
}
