package com.worldline.bootstrap.app.conf

import timber.log.Timber

fun timberConf() {
    Timber.uprootAll()
    Timber.plant(Timber.DebugTree())
}
