package com.worldline.bootstrap.presentation.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.worldline.bootstrap.presentation.ui.navigation.HomeNavGraph
import com.worldline.bootstrap.presentation.util.fadePlaceholder

@OptIn(
    ExperimentalAnimationApi::class,
)
@HomeNavGraph(
    start = true
)
@Destination(
    style = HomeScreenTransitions::class
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Home Screen")

            Spacer(Modifier.height(32.dp))

            Text(
                modifier = Modifier
                    .fadePlaceholder(state.isLoading),
                text = if (state.username != null || state.isLoading) {
                    "Hello ${state.username}!"
                } else {
                    "Hello user, please set your username next time you log in."
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
