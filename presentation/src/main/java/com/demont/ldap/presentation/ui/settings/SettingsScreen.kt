package com.demont.ldap.presentation.ui.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demont.ldap.domain.model.Theme
import com.demont.ldap.presentation.ui.destinations.WelcomeScreenDestination
import com.demont.ldap.presentation.ui.navigation.HomeNavGraph
import com.demont.ldap.presentation.util.rememberFlowWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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