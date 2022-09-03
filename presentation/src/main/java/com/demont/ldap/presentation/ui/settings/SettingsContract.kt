package com.demont.ldap.presentation.ui.settings

import androidx.compose.runtime.Immutable
import com.demont.ldap.presentation.ui.base.ViewEffect
import com.demont.ldap.presentation.ui.base.ViewEvent
import com.demont.ldap.presentation.ui.base.ViewState

class SettingsContract {
    @Immutable
    sealed class SettingsViewEvent : ViewEvent {
        data class SetLDAPServer(val server: String) : SettingsViewEvent()
    }

    @Immutable
    sealed class SettingsViewEffect : ViewEffect

    @Immutable
    data class SettingsViewState(
        val server: String
    ) : ViewState {
        companion object {
            fun initial() = SettingsViewState(
                server = "localhost"
            )
        }
    }
}
