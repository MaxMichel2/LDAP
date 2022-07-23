package com.demont.ldap.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.demont.ldap.domain.business.preferences.GetUsernameUseCase
import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.domain.model.data
import com.demont.ldap.presentation.ui.base.BaseViewModel
import com.demont.ldap.presentation.ui.base.Reducer
import com.demont.ldap.presentation.ui.home.HomeContract.HomeViewEffect
import com.demont.ldap.presentation.ui.home.HomeContract.HomeViewEvent
import com.demont.ldap.presentation.ui.home.HomeContract.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsernameUseCase: GetUsernameUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<HomeViewState, HomeViewEvent, HomeViewEffect>() {

    private val reducer = HomeReducer(HomeViewState.initial())

    override val state: StateFlow<HomeViewState>
        get() = reducer.state

    override val event: SharedFlow<HomeViewEvent>
        get() = reducer.event

    override val effect: Flow<HomeViewEffect>
        get() = reducer.effect

    init {
        viewModelScope.launch(dispatcher) {
            getUsernameUseCase(Unit).collect { result ->
                sendEvent(HomeViewEvent.UpdateLoading(result.isLoading()))
                if (result.isSuccessful()) {
                    sendEvent(HomeViewEvent.UpdateUsername(result.data))
                }
            }
        }
    }

    private fun sendEvent(event: HomeViewEvent) {
        reducer.sendEvent(event)
    }

    private class HomeReducer(initial: HomeViewState) :
        Reducer<HomeViewState, HomeViewEvent, HomeViewEffect>(initial) {
        override fun reduce(oldState: HomeViewState, event: HomeViewEvent) {
            when (event) {
                is HomeViewEvent.UpdateLoading -> setState(
                    oldState.copy(
                        isLoading = event.isLoading
                    )
                )
                is HomeViewEvent.UpdateUsername -> setState(
                    oldState.copy(
                        username = event.username,
                    )
                )
            }
        }
    }
}
