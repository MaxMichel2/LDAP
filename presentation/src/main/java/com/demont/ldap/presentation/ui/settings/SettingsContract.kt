package com.demont.ldap.presentation.ui.settings

import androidx.compose.runtime.Immutable
import com.demont.ldap.domain.model.Theme
import com.demont.ldap.presentation.ui.base.ViewEffect
import com.demont.ldap.presentation.ui.base.ViewEvent
import com.demont.ldap.presentation.ui.base.ViewState

class SettingsContract {
    @Immutable
    sealed class SettingsViewEvent : ViewEvent {
        data class SetTheme(val theme: Theme) : SettingsViewEvent()
    }

    @Immutable
    sealed class SettingsViewEffect : ViewEffect {
        object NavigateToWelcome : SettingsViewEffect()
    }

    @Immutable
    data class SettingsViewState(
        val selectedTheme: Theme
    ) : ViewState {
        companion object {
            fun initial() = SettingsViewState(
                selectedTheme = Theme.SYSTEM
            )
        }
    }
}
