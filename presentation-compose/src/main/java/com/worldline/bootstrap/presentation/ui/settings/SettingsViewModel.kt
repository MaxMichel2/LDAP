package com.worldline.bootstrap.presentation.ui.settings

import androidx.lifecycle.viewModelScope
import com.worldline.bootstrap.domain.business.preferences.GetThemeUseCase
import com.worldline.bootstrap.domain.business.preferences.LogoutUseCase
import com.worldline.bootstrap.domain.business.preferences.SetThemeUseCase
import com.worldline.bootstrap.domain.di.IoDispatcher
import com.worldline.bootstrap.domain.model.Theme
import com.worldline.bootstrap.domain.model.data
import com.worldline.bootstrap.domain.model.onSuccess
import com.worldline.bootstrap.presentation.ui.base.BaseViewModel
import com.worldline.bootstrap.presentation.ui.base.Reducer
import com.worldline.bootstrap.presentation.ui.settings.SettingsContract.*
import com.worldline.bootstrap.presentation.ui.settings.theme.ThemeDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    themeDelegate: ThemeDelegate,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<SettingsViewState, SettingsViewEvent, SettingsViewEffect>(),
    ThemeDelegate by themeDelegate {

    private val reducer = SettingsReducer(SettingsViewState.initial())

    override val state: StateFlow<SettingsViewState>
        get() = reducer.state

    override val event: SharedFlow<SettingsViewEvent>
        get() = reducer.event

    override val effect: Flow<SettingsViewEffect>
        get() = reducer.effect

    init {
        runBlocking {
            getThemeUseCase(Unit).collect { result ->
                if (result.isSuccessful()) {
                    result.data?.let { theme ->
                        sendEvent(SettingsViewEvent.SetTheme(theme))
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(dispatcher) {
            logoutUseCase(Unit)
            reducer.sendEffect(SettingsViewEffect.NavigateToWelcome)
        }
    }

    fun changeTheme(theme: Theme) {
        viewModelScope.launch(dispatcher) {
            setThemeUseCase(theme.storageKey).onSuccess {
                sendEvent(SettingsViewEvent.SetTheme(theme))
            }
        }
    }

    private fun sendEvent(event: SettingsViewEvent) {
        reducer.sendEvent(event)
    }

    private class SettingsReducer(initial: SettingsViewState) :
        Reducer<SettingsViewState, SettingsViewEvent, SettingsViewEffect>(initial) {
        override fun reduce(oldState: SettingsViewState, event: SettingsViewEvent) {
            when (event) {
                is SettingsViewEvent.SetTheme -> setState(
                    oldState.copy(
                        selectedTheme = event.theme
                    )
                )
            }
        }
    }
}
