package com.worldline.bootstrap.presentation.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.worldline.bootstrap.presentation.ui.appDestination
import com.worldline.bootstrap.presentation.ui.destinations.WelcomeScreenDestination

@ExperimentalAnimationApi
object HomeScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            WelcomeScreenDestination -> slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(450)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            WelcomeScreenDestination -> slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(450)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return when (initialState.appDestination()) {
            WelcomeScreenDestination -> slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(450)
            )
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            WelcomeScreenDestination -> slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(450)
            )
            else -> null
        }
    }
}
