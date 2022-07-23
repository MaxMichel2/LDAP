package com.worldline.bootstrap.presentation.ui.welcome

import androidx.lifecycle.viewModelScope
import com.worldline.bootstrap.domain.business.preferences.GetUsernameUseCase
import com.worldline.bootstrap.domain.business.preferences.LoginUseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.data
import com.worldline.bootstrap.domain.model.onSuccess
import com.worldline.bootstrap.presentation.ui.base.BaseViewModel
import com.worldline.bootstrap.presentation.ui.base.Reducer
import com.worldline.bootstrap.presentation.ui.welcome.WelcomeContract.WelcomeViewEffect
import com.worldline.bootstrap.presentation.ui.welcome.WelcomeContract.WelcomeViewEvent
import com.worldline.bootstrap.presentation.ui.welcome.WelcomeContract.WelcomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val getUsernameUseCase: GetUsernameUseCase,
    private val loginUseCase: LoginUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<WelcomeViewState, WelcomeViewEvent, WelcomeViewEffect>() {

    private val reducer = WelcomeReducer(WelcomeViewState.initial())

    override val state: StateFlow<WelcomeViewState>
        get() = reducer.state

    override val event: SharedFlow<WelcomeViewEvent>
        get() = reducer.event

    override val effect: Flow<WelcomeViewEffect>
        get() = reducer.effect

    init {
        viewModelScope.launch(dispatcher) {
            getUsernameUseCase(Unit).collect { result ->
                sendEvent(WelcomeViewEvent.UpdateLoading(result.isLoading()))
                if (result.isSuccessful()) {
                    sendEvent(WelcomeViewEvent.UpdateUsername(result.data))
                }
            }
        }
    }

    private fun sendEvent(event: WelcomeViewEvent) {
        reducer.sendEvent(event)
    }

    private fun sendEffect(effect: WelcomeViewEffect) {
        reducer.sendEffect(effect)
    }

    fun login(username: String) {
        viewModelScope.launch(dispatcher) {
            val usernameToSave = username.ifBlank { null }
            loginUseCase(usernameToSave).onSuccess {
                sendEffect(WelcomeViewEffect.NavigateToHome)
            }
        }
    }

    private class WelcomeReducer(initial: WelcomeViewState) :
        Reducer<WelcomeViewState, WelcomeViewEvent, WelcomeViewEffect>(initial) {
        override fun reduce(oldState: WelcomeViewState, event: WelcomeViewEvent) {
            when (event) {
                is WelcomeViewEvent.UpdateLoading -> setState(
                    oldState.copy(
                        isLoading = event.isLoading
                    )
                )
                is WelcomeViewEvent.UpdateUsername -> setState(
                    oldState.copy(
                        username = event.username,
                    )
                )
            }
        }
    }
}
