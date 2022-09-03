package com.demont.ldap.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.demont.ldap.presentation.ui.components.navigation.ScaffoldDestination
import com.demont.ldap.presentation.ui.destinations.DirectionDestination
import com.demont.ldap.presentation.ui.destinations.HomeScreenDestination
import com.demont.ldap.presentation.ui.destinations.SettingsScreenDestination
import com.demont.ldap.presentation.ui.home.HomeViewModel
import com.demont.ldap.presentation.ui.settings.SettingsViewModel
import com.demont.ldap.presentation.ui.theme.BootstrapTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo

@Suppress("LongMethod")
@Composable
fun BootstrapApp() {
    BootstrapTheme {
        val engine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING
        )
        val navController = engine.rememberNavController()

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Settings
        )

        ScaffoldDestination(
            modifier = Modifier
                .fillMaxSize(),
            startRoute = NavGraphs.root.startRoute,
            navController = navController,
            bottomBar = {
                NavigationBar {
                    items.forEach { navigationItem ->
                        val selected = navigationItem.destination == it
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (selected) {
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
                                    imageVector = if (selected) {
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
        ) { paddingValues ->
            DestinationsNavHost(
                modifier = Modifier
                    .padding(paddingValues),
                navGraph = NavGraphs.root,
                engine = engine,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(HomeScreenDestination) { hiltViewModel<HomeViewModel>() }

                    dependency(SettingsScreenDestination) { hiltViewModel<SettingsViewModel>() }
                }
            )
        }
    }
}

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

    object Settings : NavigationItem(
        label = "Settings",
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        destination = SettingsScreenDestination
    )
}
