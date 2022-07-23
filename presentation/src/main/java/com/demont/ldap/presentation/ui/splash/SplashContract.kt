package com.demont.ldap.presentation.ui.splash

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Immutable
import com.demont.ldap.presentation.ui.base.ViewEffect
import com.demont.ldap.presentation.ui.base.ViewEvent
import com.demont.ldap.presentation.ui.base.ViewState
import com.demont.ldap.presentation.ui.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.spec.Route

class SplashContract {
    @Immutable
    sealed class SplashViewEvent : ViewEvent {
        data class UpdateLoading(val isLoading: Boolean) : SplashViewEvent()
        data class UpdateStartDestination(val destination: Route) : SplashViewEvent()
    }

    @Immutable
    sealed class SplashViewEffect : ViewEffect

    @ExperimentalAnimationApi
    @Immutable
    data class SplashViewState(
        val isLoading: Boolean,
        val startRoute: Route
    ) : ViewState {
        companion object {
            fun initial() = SplashViewState(
                isLoading = true,
                startRoute = WelcomeScreenDestination
            )
        }
    }
}
