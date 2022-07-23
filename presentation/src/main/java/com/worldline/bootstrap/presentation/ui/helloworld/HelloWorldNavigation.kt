package com.worldline.bootstrap.presentation.ui.helloworld

sealed class HelloWorldNavigation {
    data class NavigateToGoodbye(val name: String) : HelloWorldNavigation()
}
