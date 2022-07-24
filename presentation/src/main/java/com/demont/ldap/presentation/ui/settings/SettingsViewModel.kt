package com.demont.ldap.presentation.ui.settings

import com.demont.ldap.domain.di.IoDispatcher
import com.demont.ldap.presentation.ui.base.BaseViewModel
import com.demont.ldap.presentation.ui.base.Reducer
import com.demont.ldap.presentation.ui.settings.SettingsContract.SettingsViewEffect
import com.demont.ldap.presentation.ui.settings.SettingsContract.SettingsViewEvent
import com.demont.ldap.presentation.ui.settings.SettingsContract.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<SettingsViewState, SettingsViewEvent, SettingsViewEffect>() {

    private val reducer = SettingsReducer(SettingsViewState.initial())

    override val state: StateFlow<SettingsViewState>
        get() = reducer.state

    override val event: SharedFlow<SettingsViewEvent>
        get() = reducer.event

    override val effect: Flow<SettingsViewEffect>
        get() = reducer.effect

    init {
        // TODO - Get stored LDAP server information for potential editing
    }

    private fun sendEvent(event: SettingsViewEvent) {
        reducer.sendEvent(event)
    }

    private class SettingsReducer(initial: SettingsViewState) :
        Reducer<SettingsViewState, SettingsViewEvent, SettingsViewEffect>(initial) {
        override fun reduce(oldState: SettingsViewState, event: SettingsViewEvent) {
            when (event) {
                is SettingsViewEvent.SetLDAPServer -> setState(
                    oldState.copy(
                        server = event.server
                    )
                )
            }
        }
    }
}
