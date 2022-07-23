package com.worldline.bootstrap.presentation.ui.splash

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.viewModelScope
import com.worldline.bootstrap.domain.business.preferences.GetAuthenticatedUseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.onSuccess
import com.worldline.bootstrap.presentation.ui.NavGraphs
import com.worldline.bootstrap.presentation.ui.base.BaseViewModel
import com.worldline.bootstrap.presentation.ui.base.Reducer
import com.worldline.bootstrap.presentation.ui.destinations.WelcomeScreenDestination
import com.worldline.bootstrap.presentation.ui.splash.SplashContract.SplashViewEffect
import com.worldline.bootstrap.presentation.ui.splash.SplashContract.SplashViewEvent
import com.worldline.bootstrap.presentation.ui.splash.SplashContract.SplashViewState
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
