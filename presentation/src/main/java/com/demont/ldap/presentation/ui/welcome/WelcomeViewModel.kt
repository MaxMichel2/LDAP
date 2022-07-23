package com.demont.ldap.presentation.ui.welcome

import androidx.lifecycle.viewModelScope
import com.demont.ldap.domain.business.preferences.GetUsernameUseCase
import com.demont.ldap.domain.business.preferences.LoginUseCase
import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.domain.model.data
import com.demont.ldap.domain.model.onSuccess
import com.demont.ldap.presentation.ui.base.BaseViewModel
import com.demont.ldap.presentation.ui.base.Reducer
import com.demont.ldap.presentation.ui.welcome.WelcomeContract.WelcomeViewEffect
import com.demont.ldap.presentation.ui.welcome.WelcomeContract.WelcomeViewEvent
import com.demont.ldap.presentation.ui.welcome.WelcomeContract.WelcomeViewState
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
