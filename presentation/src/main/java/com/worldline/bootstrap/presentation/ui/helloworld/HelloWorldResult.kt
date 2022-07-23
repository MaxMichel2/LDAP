package com.worldline.bootstrap.presentation.ui.helloworld

data class HelloWorldResult(val helloWorldResultStatus: HelloWorldResultStatus, val value: String)

enum class HelloWorldResultStatus {
    OK,
    KO
}
