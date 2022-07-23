package com.worldline.bootstrap.presentation.ui.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.worldline.bootstrap.domain.model.Theme
import com.worldline.bootstrap.presentation.ui.destinations.WelcomeScreenDestination
import com.worldline.bootstrap.presentation.ui.navigation.HomeNavGraph
import com.worldline.bootstrap.presentation.util.rememberFlowWithLifecycle

@OptIn(
    ExperimentalAnimationApi::class,
)
@HomeNavGraph
@Destination(
    style = SettingsScreenTransitions::class
)
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val effect = rememberFlowWithLifecycle(viewModel.effect)
    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is SettingsContract.SettingsViewEffect.NavigateToWelcome -> navigator.navigate(
                    WelcomeScreenDestination
                ) {
                    launchSingleTop = true
                }
            }
        }
    }

    SettingsScreenContent(
        logoutButtonClick = {
            viewModel.logout()
        },
        themes = Theme.available(),
        selectedTheme = state.selectedTheme,
        selectTheme = { theme ->
            viewModel.changeTheme(theme)
        }
    )
}

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun SettingsScreenContent(
    logoutButtonClick: () -> Unit = {},
    themes: List<Theme>,
    selectedTheme: Theme,
    selectTheme: (Theme) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Settings Screen")

            Spacer(Modifier.height(32.dp))

            if (themes.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    themes.forEach { theme ->
                        FilterChip(
                            selected = selectedTheme == theme,
                            onClick = { selectTheme(theme) },
                            selectedIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = theme.name
                                )
                            }
                        )
                    }
                }
            }

            Button(
                onClick = logoutButtonClick
            ) {
                Text("Logout")
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreenContent(
        themes = Theme.available(),
        selectedTheme = Theme.SYSTEM,
        selectTheme = {}
    )
}
