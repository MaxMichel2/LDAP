package com.worldline.bootstrap.presentation.ui.components.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.spec.Route
import com.worldline.bootstrap.presentation.ui.appCurrentDestinationAsState
import com.worldline.bootstrap.presentation.ui.destinations.Destination
import com.worldline.bootstrap.presentation.ui.startAppDestination

/**
 * Scaffold extension to wrap [topBar] and [bottomBar] with [Destination]s. The use lies in using
 * the destination to identify whether or not to show either navigation bar.
 *
 * @param modifier The [Modifier] to be applied to this scaffold
 * @param topBar Top app bar of the screen, typically a [SmallTopAppBar]
 * @param bottomBar Bottom bar of the screen, typically a [NavigationBar]
 * @param snackbarHost Component to host [Snackbar]s that are pushed to be shown via
 * [SnackbarHostState.showSnackbar], typically a [SnackbarHost]
 * @param floatingActionButton Main action button of the screen, typically a [FloatingActionButton]
 * @param floatingActionButtonPosition Position of the FAB on the screen. See [FabPosition].
 * @param containerColor The color used for the background of this scaffold. Use [Color.Transparent]
 * to have no color.
 * @param contentColor The preferred color for content inside this scaffold. Defaults to either the
 * matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param content Content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root via [Modifier.padding] to properly offset top and bottom bars. If
 * using [Modifier.verticalScroll], apply this modifier to the child of the scroll, and not on
 * the scroll itself.
 */
@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ScaffoldDestination(
    modifier: Modifier = Modifier,
    startRoute: Route,
    navController: NavHostController,
    topBar: @Composable (Destination, NavBackStackEntry?) -> Unit = { _, _ -> },
    bottomBar: @Composable (Destination) -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination
    val navBackStackEntry = navController.currentBackStackEntry

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    // 👇 ModalBottomSheetLayout is only needed if some destination is bottom sheet styled
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(16.dp)
    ) {
        Scaffold(
            modifier = modifier,
            topBar = { topBar(destination, navBackStackEntry) },
            bottomBar = { bottomBar(destination) },
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            content = content
        )
    }
}
