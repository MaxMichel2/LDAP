package com.worldline.bootstrap.domain.model

import android.os.Build

/**
 * Represents the available UI themes for the application
 */
enum class Theme(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system"),
    BATTERY_SAVER("battery_saver");

    companion object {
        fun available(): List<Theme> {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values().filterNot { theme ->
                    theme == BATTERY_SAVER
                }
            } else {
                values().filterNot { theme ->
                    theme == SYSTEM
                }
            }
        }
    }
}

/**
 * Returns the matching [Theme] for the given [storageKey] value.
 */
fun themeFromStorageKey(storageKey: String): Theme {
    return Theme.values().first { it.storageKey == storageKey }
}
