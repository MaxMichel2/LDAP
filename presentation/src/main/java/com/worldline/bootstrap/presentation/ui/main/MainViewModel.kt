package com.worldline.bootstrap.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.worldline.bootstrap.presentation.ui.settings.theme.ThemeDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    themeDelegate: ThemeDelegate
) : ViewModel(), ThemeDelegate by themeDelegate
