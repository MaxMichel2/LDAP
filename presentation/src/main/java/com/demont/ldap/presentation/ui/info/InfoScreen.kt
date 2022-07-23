package com.demont.ldap.presentation.ui.info

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.demont.ldap.presentation.ui.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(
    ExperimentalAnimationApi::class,
)
@HomeNavGraph
@Destination(
    style = InfoScreenTransitions::class
)
@Composable
fun InfoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "This is an info screen"
        )
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    InfoScreen()
}
