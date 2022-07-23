package com.worldline.bootstrap.presentation.ui.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(
    start = true
)
@NavGraph
annotation class HomeNavGraph(
    val start: Boolean = false
)

/**
 * This annotation is required when creating a nested Nav Graph an is used here as an example.
 *
 * If adding this annotation, a [Composable] must be annotated with `@<CustomNavGraph>(start = true)`
 * else the ksp code generation will fail.
 */
// @RootNavGraph
@NavGraph
annotation class CustomNavGraph(
    val start: Boolean = false
)
