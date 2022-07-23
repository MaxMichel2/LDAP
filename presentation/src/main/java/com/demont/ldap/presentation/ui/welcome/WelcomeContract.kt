package com.demont.ldap.presentation.ui.welcome

import androidx.compose.runtime.Immutable
import com.demont.ldap.presentation.ui.base.ViewEffect
import com.demont.ldap.presentation.ui.base.ViewEvent
import com.demont.ldap.presentation.ui.base.ViewState

class WelcomeContract {
    @Immutable
    sealed class WelcomeViewEvent : ViewEvent {
        data class UpdateLoading(val isLoading: Boolean) : WelcomeViewEvent()
        data class UpdateUsername(val username: String?) : WelcomeViewEvent()
    }

    @Immutable
    sealed class WelcomeViewEffect : ViewEffect {
        object NavigateToHome : WelcomeViewEffect()
    }

    @Immutable
    data class WelcomeViewState(
        val isLoading: Boolean,
        val username: String?
    ) : ViewState {
        companion object {
            fun initial() = WelcomeViewState(
                isLoading = true,
                username = null
            )
        }
    }
}
