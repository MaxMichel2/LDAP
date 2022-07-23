package com.demont.ldap.presentation.ui.settings.theme

import com.demont.ldap.domain.model.Theme
import com.demont.ldap.domain.preferences.PreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * Interface to implement activity theming via a ViewModel.
 *
 * You can inject a implementation of this via Dagger2, then use the implementation as an interface
 * delegate to add the functionality without writing any code
 *
 * Example usage:
 * ```
 * class MyViewModel @Inject constructor(
 *     themeDelegate: ThemeDelegate
 * ) : ViewModel(), ThemeDelegate by themeDelegate {
 * ```
 */
interface ThemeDelegate {
    /**
     * Allows observing of the current theme
     */
    val theme: Flow<Theme>

    /**
     * Allows querying of the current theme synchronously
     */
    val currentTheme: Theme
}

data class ThemeDelegateImpl @Inject constructor(
    private val repository: PreferenceRepository,
) : ThemeDelegate {

    override val theme: Flow<Theme> = repository.theme

    override val currentTheme: Theme
        get() = runBlocking { // Using runBlocking to execute this coroutine synchronously
            repository.theme.first()
        }
}
