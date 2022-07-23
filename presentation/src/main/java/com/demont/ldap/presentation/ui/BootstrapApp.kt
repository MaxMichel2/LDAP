package com.demont.ldap.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.demont.ldap.presentation.ui.components.navigation.ScaffoldDestination
import com.demont.ldap.presentation.ui.destinations.Destination
import com.demont.ldap.presentation.ui.destinations.DirectionDestination
import com.demont.ldap.presentation.ui.destinations.HomeScreenDestination
import com.demont.ldap.presentation.ui.destinations.InfoScreenDestination
import com.demont.ldap.presentation.ui.destinations.SettingsScreenDestination
import com.demont.ldap.presentation.ui.destinations.WelcomeScreenDestination
import com.demont.ldap.presentation.ui.theme.BootstrapTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.Route
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

@Suppress("LongMethod")
@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun BootstrapApp(
    startRoute: Route
) {
    BootstrapTheme {
        val engine = rememberAnimatedNavHostEngine()
        val navController = engine.rememberNavController()

        val currentDestination: Destination = navController.appCurrentDestinationAsState().value
            ?: WelcomeScreenDestination

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Info,
            NavigationItem.Settings
        )

        ScaffoldDestination(
            modifier = Modifier
                .fillMaxSize(),
            startRoute = startRoute,
            navController = navController,
            bottomBar = {
                AnimatedVisibility(it.shouldShowScaffoldElements) {
                    NavigationBar {
                        items.forEach { navigationItem ->
                            val isCurrentDestOnBackStack =
                                navController.isRouteOnBackStack(navigationItem.destination)
                            NavigationBarItem(
                                selected = isCurrentDestOnBackStack,
                                onClick = {
                                    if (isCurrentDestOnBackStack) {
                                        // When we click again on a bottom bar item and it was already
                                        // selected, we want to pop the back stack until the initial
                                        // destination of this bottom bar item
                                        navController.popBackStack(
                                            navigationItem.destination,
                                            false
                                        )
                                        return@NavigationBarItem
                                    }

                                    navController.navigate(navigationItem.destination) {
                                        // Pop up to the root of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(NavGraphs.root) {
                                            saveState = true
                                        }

                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true

                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (isCurrentDestOnBackStack) {
                                            navigationItem.selectedIcon
                                        } else {
                                            navigationItem.unselectedIcon
                                        },
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = navigationItem.label
                                    )
                                }
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            DestinationsNavHost(
                modifier = Modifier
                    .padding(paddingValues),
                navGraph = NavGraphs.root,
                startRoute = startRoute,
                engine = engine,
                navController = navController
            )
        }
    }
}

@OptIn(
    ExperimentalAnimationApi::class,
)
private val Destination.shouldShowScaffoldElements
    get() = this in NavGraphs.home.destinations

@OptIn(
    ExperimentalAnimationApi::class,
)
sealed class NavigationItem(
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val destination: DirectionDestination
) {
    object Home : NavigationItem(
        label = "Home",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        destination = HomeScreenDestination
    )

    object Info : NavigationItem(
        label = "Info",
        unselectedIcon = Icons.Outlined.Info,
        selectedIcon = Icons.Filled.Info,
        destination = InfoScreenDestination
    )

    object Settings : NavigationItem(
        label = "Settings",
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        destination = SettingsScreenDestination
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun BootstrapAppPreview() {
    BootstrapTheme {
        BootstrapApp(
            startRoute = WelcomeScreenDestination
        )
    }
}
