package com.demont.ldap.presentation.ui.main

import android.app.role.RoleManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.demont.ldap.presentation.ui.BootstrapApp
import com.demont.ldap.presentation.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private val preDrawListener = ViewTreeObserver.OnPreDrawListener { false }

    private val roleRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Timber.i("I am now the screening app")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO - Get user permissions on app start for: PHONE_STATE, CALL_LOG, INTERNET

        val splashScreen = installSplashScreen()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestRole()
        }

        setContent {
            val state by splashViewModel.state.collectAsState()

            splashScreen.setKeepOnScreenCondition {
                state.isLoading
            }

            BootstrapApp()
        }

        unblockDrawing()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestRole() {
        val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        roleRequest.launch(intent)
    }

    private fun unblockDrawing() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        content.viewTreeObserver.addOnPreDrawListener { true }
    }
}
