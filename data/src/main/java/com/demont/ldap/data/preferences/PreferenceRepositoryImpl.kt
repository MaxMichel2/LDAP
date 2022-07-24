package com.demont.ldap.data.preferences

import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.demont.ldap.domain.model.PreferenceKey
import com.demont.ldap.domain.model.Theme
import com.demont.ldap.domain.model.themeFromStorageKey
import com.demont.ldap.domain.preferences.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Data store instance used to store application data.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bootstrap-preferences")

@Suppress("CommentOverPrivateFunction")
class PreferenceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferenceRepository {

    /**
     * Update a value in the [DataStore] for the specified [key] with the specified [value] of type
     * [T].
     *
     * @param T The type of the data to be stored
     * @param key The [PreferenceKey] of the stored data
     * @param value The new value to store
     */
    override suspend fun <T> updatePreference(key: PreferenceKey, value: T) {
        @Suppress("UNCHECKED_CAST")
        val prefKey: Preferences.Key<T> = getKey(key) as Preferences.Key<T>
        context.dataStore.edit { settings ->
            settings[prefKey] = value
        }
    }

    /**
     * Get a value in the [DataStore] for the specified [key] of type [T].
     *
     * @param T The type of the data to be retrieved
     * @param key The [PreferenceKey] of the stored data
     * @return The stored data or null (of type T?) wrapped in a [Flow]
     */
    override suspend fun <T> getPreference(key: PreferenceKey): Flow<T?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            @Suppress("UNCHECKED_CAST")
            val prefKey: Preferences.Key<T> = getKey(key) as Preferences.Key<T>
            preferences[prefKey]
        }

    /**
     * Get the currently stored [Theme] wrapped in a [Flow].
     */
    override val theme: Flow<Theme> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val currentTheme = preferences[THEME]
            when {
                currentTheme != null -> themeFromStorageKey(currentTheme)
                // Provide defaults for when there is no theme set
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> Theme.SYSTEM
                else -> Theme.BATTERY_SAVER
            }
        }

    /**
     * Mapping function between [PreferenceKey]s and [Preferences.Key]s in the companion object.
     *
     * @param key The [PreferenceKey] to map
     * @return The mapped [Preferences.Key]
     */
    private fun getKey(key: PreferenceKey): Preferences.Key<*> {
        return when (key) {
            PreferenceKey.USERNAME -> USERNAME
            PreferenceKey.AUTHENTICATED -> AUTHENTICATED
            PreferenceKey.THEME -> THEME
            PreferenceKey.CALLING_PHONE_NUMBER -> CALLING_PHONE_NUMBER
        }
    }

    companion object {
        val USERNAME = stringPreferencesKey("username")

        /**
         * This preference could also be a [stringPreferencesKey] containing an Access Token based on
         * the application requirements.
         */
        val AUTHENTICATED = booleanPreferencesKey("authenticated")

        /**
         * This preference stores the currently selected [Theme]. The default value would be
         * [Theme.SYSTEM] or [Theme.BATTERY_SAVER] based on the OS version.
         */
        val THEME = stringPreferencesKey("theme")

        val CALLING_PHONE_NUMBER = stringPreferencesKey("calling_phone_number")
    }
}
