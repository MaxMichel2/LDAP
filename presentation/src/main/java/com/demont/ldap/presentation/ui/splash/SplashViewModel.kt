package com.demont.ldap.presentation.ui.splash

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.viewModelScope
import com.demont.ldap.domain.business.preferences.GetAuthenticatedUseCase
import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.domain.model.onSuccess
import com.demont.ldap.presentation.ui.NavGraphs
import com.demont.ldap.presentation.ui.base.BaseViewModel
import com.demont.ldap.presentation.ui.base.Reducer
import com.demont.ldap.presentation.ui.destinations.WelcomeScreenDestination
import com.demont.ldap.presentation.ui.splash.SplashContract.SplashViewEffect
import com.demont.ldap.presentation.ui.splash.SplashContract.SplashViewEvent
import com.demont.ldap.presentation.ui.splash.SplashContract.SplashViewState
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
class SplashViewModel @Inject constructor(
    private val getAuthenticatedUseCase: GetAuthenticatedUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<SplashViewState, SplashViewEvent, SplashViewEffect>() {

    private val reducer = SplashReducer(SplashViewState.initial())

    override val state: StateFlow<SplashViewState>
        get() = reducer.state

    override val event: SharedFlow<SplashViewEvent>
        get() = reducer.event

    override val effect: Flow<SplashViewEffect>
        get() = reducer.effect

    init {

        viewModelScope.launch(dispatcher) {
            sendEvent(SplashViewEvent.UpdateLoading(true))
            getAuthenticatedUseCase(Unit).onSuccess { isAuthenticated ->
                sendEvent(SplashViewEvent.UpdateLoading(false))
                when (isAuthenticated) {
                    true -> sendEvent(SplashViewEvent.UpdateStartDestination(NavGraphs.home))
                    false -> sendEvent(
                        SplashViewEvent.UpdateStartDestination(
                            WelcomeScreenDestination
                        )
                    )
                }
            }
        }
    }

    private fun sendEvent(event: SplashViewEvent) {
        reducer.sendEvent(event)
    }

    private class SplashReducer(initial: SplashViewState) :
        Reducer<SplashViewState, SplashViewEvent, SplashViewEffect>(initial) {
        override fun reduce(oldState: SplashViewState, event: SplashViewEvent) {
            when (event) {
                is SplashViewEvent.UpdateLoading -> setState(
                    oldState.copy(
                        isLoading = event.isLoading
                    )
                )
                is SplashViewEvent.UpdateStartDestination -> setState(
                    oldState.copy(
                        startRoute = event.destination
                    )
                )
            }
        }
    }
}
