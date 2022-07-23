package com.demont.ldap.presentation.ui.main

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.demont.ldap.presentation.ui.BootstrapApp
import com.demont.ldap.presentation.ui.splash.SplashViewModel
import com.demont.ldap.presentation.util.extensions.updateForTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalAnimationApi::class,
)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private val viewModel: MainViewModel by viewModels()

    private val preDrawListener = ViewTreeObserver.OnPreDrawListener { false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateForTheme(viewModel.currentTheme)

        val splashScreen = installSplashScreen()

        setContent {
            val state by splashViewModel.state.collectAsState()

            splashScreen.setKeepOnScreenCondition {
                state.isLoading
            }

            BootstrapApp(
                startRoute = state.startRoute
            )
        }

        lifecycleScope.launch {
            viewModel.theme
                .flowWithLifecycle(lifecycle)
                .collect { theme ->
                    updateForTheme(theme)
                }
        }

        unblockDrawing()
    }

    private fun unblockDrawing() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        content.viewTreeObserver.addOnPreDrawListener { true }
    }
}
