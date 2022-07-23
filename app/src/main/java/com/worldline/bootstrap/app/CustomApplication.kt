package com.worldline.bootstrap.app

import android.app.Application
import com.worldline.bootstrap.app.conf.timberConf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        timberConf()
    }
}
