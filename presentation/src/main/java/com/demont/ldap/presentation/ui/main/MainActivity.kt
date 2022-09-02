package com.demont.ldap.presentation.ui.main

import android.Manifest
import android.app.role.RoleManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.forEach { entry ->
                val permission = entry.key
                val isGranted = entry.value

                when (permission) {
                    Manifest.permission.READ_PHONE_STATE -> {
                        if (isGranted) {
                            Timber.i("Phone permission granted")
                        }
                    }
                    Manifest.permission.READ_CALL_LOG -> {
                        if (isGranted) {
                            Timber.i("Call log permission granted")
                        }
                    }
                    Manifest.permission.INTERNET -> {
                        if (isGranted) {
                            Timber.i("Internet permission granted")
                        }
                    }
                    else -> {
                        Timber.i("Something went wrong and not all permissions were granted OR some other permission was requested")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO - Get user permissions on app start for: PHONE_STATE, CALL_LOG, INTERNET

        val splashScreen = installSplashScreen()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestRole()
        } else {
            checkPermissions()
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

    private fun checkPermissions() {
        val readPhoneStatePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED

        val internetPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED

        val readCallLogPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED

        if (!readPhoneStatePermission || !internetPermission || !readCallLogPermission) {

            when {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) -> {
                    Toast.makeText(
                        this,
                        "Phone permission required for this application to function",
                        Toast.LENGTH_LONG
                    ).show()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.INTERNET
                ) -> {
                    Toast.makeText(
                        this,
                        "Internet permission required for this application to function",
                        Toast.LENGTH_LONG
                    ).show()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CALL_LOG
                ) -> {
                    Toast.makeText(
                        this,
                        "Call log permission required for this application to function",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> permissionRequest.launch(
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_CALL_LOG
                    )
                )
            }
        }
    }

    private fun unblockDrawing() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        content.viewTreeObserver.addOnPreDrawListener { true }
    }
}
