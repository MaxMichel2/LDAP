package com.worldline.bootstrap.presentation.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Base view model following the MVI architecture (MVVM with state management).
 *
 * @param State A class that inherits from [ViewState]
 * @param Event A class that inherits from [ViewEvent]
 * @param Effect A class that inherits from [ViewEffect]
 */
abstract class BaseViewModel<State : ViewState, Event : ViewEvent, Effect : ViewEffect> :
    ViewModel() {
    abstract val state: StateFlow<State>

    abstract val event: SharedFlow<Event>

    abstract val effect: Flow<Effect>
}
