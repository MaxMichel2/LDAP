package com.worldline.bootstrap.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import com.worldline.bootstrap.presentation.ui.base.BaseActivity
import com.worldline.bootstrap.presentation.databinding.ActivityContainerBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navHostFragment.visibility = View.VISIBLE
    }
}
