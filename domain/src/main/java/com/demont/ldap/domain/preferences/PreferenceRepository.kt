package com.demont.ldap.domain.preferences

import com.demont.ldap.domain.model.PreferenceKey
import com.demont.ldap.domain.model.Theme
import kotlinx.coroutines.flow.Flow

/**
 * Repository used to handle updating and retrieving data usually stored in a DataStore.
 */
interface PreferenceRepository {
    suspend fun <T> updatePreference(key: PreferenceKey, value: T)
    suspend fun <T> getPreference(key: PreferenceKey): Flow<T?>
    val theme: Flow<Theme>
}
