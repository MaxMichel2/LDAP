package com.worldline.bootstrap.presentation.ui.helloworld

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.worldline.bootstrap.domain.api.HelloWorldService
import com.worldline.bootstrap.presentation.ui.base.BaseViewModel
import com.worldline.bootstrap.domain.model.Event

@HiltViewModel
class HelloWorldViewModel @Inject constructor(private val helloWorldService: HelloWorldService) :
    BaseViewModel() {

    private val _navigationResult = MutableLiveData<Event<HelloWorldNavigation>>()
    val navigationResult: MutableLiveData<Event<HelloWorldNavigation>> = _navigationResult

    val helloWorldResult: LiveData<HelloWorldResult> = initHelloWorld(helloWorldService)

    private fun initHelloWorld(service: HelloWorldService) = liveData {
        runCatching { service.getValueToShow() }
            .onSuccess { data ->
                emit(HelloWorldResult(HelloWorldResultStatus.OK, data.value))
            }.onFailure { error ->
                emit(HelloWorldResult(HelloWorldResultStatus.KO, error.message ?: ""))
            }
    }

    fun goodbyeButtonClicked(text: Editable) {
        val textToUse = helloWorldService.parseValue(text)
        _navigationResult.postValue(Event(HelloWorldNavigation.NavigateToGoodbye(textToUse)))
    }
}
