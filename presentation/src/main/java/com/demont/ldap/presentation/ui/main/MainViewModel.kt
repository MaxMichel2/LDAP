package com.demont.ldap.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.demont.ldap.presentation.ui.settings.theme.ThemeDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    themeDelegate: ThemeDelegate
) : ViewModel(), ThemeDelegate by themeDelegate
