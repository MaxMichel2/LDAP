package com.demont.ldap.app

import android.app.Application
import com.demont.ldap.app.conf.timberConf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        timberConf()
    }
}
