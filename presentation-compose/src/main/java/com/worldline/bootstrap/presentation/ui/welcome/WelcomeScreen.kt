package com.worldline.bootstrap.presentation.ui.welcome

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.worldline.bootstrap.presentation.ui.NavGraphs
import com.worldline.bootstrap.presentation.util.fadePlaceholder
import com.worldline.bootstrap.presentation.util.rememberFlowWithLifecycle

@OptIn(
    ExperimentalAnimationApi::class
)
@Destination(
    style = WelcomeScreenTransitions::class
)
@Composable
fun WelcomeScreen(
    navigator: DestinationsNavigator,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is WelcomeContract.WelcomeViewEffect.NavigateToHome -> navigator.navigate(
                    NavGraphs.home,
                    onlyIfResumed = true
                )
            }
        }
    }

    val state by viewModel.state.collectAsState()

    var username by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        state.username?.let { username = it }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Welcome Screen")

            Spacer(Modifier.height(32.dp))

            Text(
                modifier = Modifier
                    .fadePlaceholder(state.isLoading),
                text = if (state.username != null || state.isLoading) {
                    "Hello ${state.username}, you can change your username below."
                } else {
                    "Hello, please set your username below."
                },
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = username,
                onValueChange = { username = it }
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.login(username)
                }
            ) {
                Text("Login")
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        navigator = EmptyDestinationsNavigator,
    )
}
