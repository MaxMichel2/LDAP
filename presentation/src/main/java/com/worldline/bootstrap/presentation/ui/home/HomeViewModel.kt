package com.worldline.bootstrap.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.worldline.bootstrap.domain.business.preferences.GetUsernameUseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.data
import com.worldline.bootstrap.presentation.ui.base.BaseViewModel
import com.worldline.bootstrap.presentation.ui.base.Reducer
import com.worldline.bootstrap.presentation.ui.home.HomeContract.HomeViewEffect
import com.worldline.bootstrap.presentation.ui.home.HomeContract.HomeViewEvent
import com.worldline.bootstrap.presentation.ui.home.HomeContract.HomeViewState
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
