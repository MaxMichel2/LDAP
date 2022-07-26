package com.demont.ldap.presentation.ui.home

import androidx.compose.runtime.Immutable
import com.demont.ldap.presentation.ui.base.ViewEffect
import com.demont.ldap.presentation.ui.base.ViewEvent
import com.demont.ldap.presentation.ui.base.ViewState

class HomeContract {
    @Immutable
    sealed class HomeViewEvent : ViewEvent {
        data class UpdateLoading(val isLoading: Boolean) : HomeViewEvent()
        data class UpdateUsername(val username: String?) : HomeViewEvent()
    }

    @Immutable
    sealed class HomeViewEffect : ViewEffect

    @Immutable
    data class HomeViewState(
        val isLoading: Boolean,
        val username: String?
    ) : ViewState {
        companion object {
            fun initial() = HomeViewState(
                isLoading = true,
                username = null
            )
        }
    }
}
