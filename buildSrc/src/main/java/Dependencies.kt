import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object Versions {
    const val MAJOR_VERSION = 1
    const val MINOR_VERSION = 0
    const val PATCH_VERSION = 0
    const val BUILD_VERSION = 0

    // Maximum version code authorized by Android: 2_100_000_000
    const val MULTIPLIER_MAJOR = 100_000_000
    const val MULTIPLIER_MINOR = 1_000_000
    const val MULTIPLIER_PATCH = 10_000
    const val COMMIT_COUNT = 0

    const val TIMEOUT_MINUTES = 60L

    const val COMPILE_SDK = 32
    const val MIN_SDK = 21
    const val TARGET_SDK = 32

    const val NAME: String = "$MAJOR_VERSION.$MINOR_VERSION.$PATCH_VERSION.$BUILD_VERSION"
    val CODE: Int

    init {
        /**
         * This variable **SHOULD** count all commits. However this CLI instruction has issues in
         * that it can occasionally return a lower value even after a new commit. It is recommended
         * to use a hard-coded value and make sure to increment it in this file when a new release
         * is pushed.
         */
        val commitCount = "git rev-list --count HEAD".execute()?.trim()?.toInt() ?: 0

        /**
         * If the `NAME` is 1.3.6.7 for example, the `CODE` will look like the following:
         *
         * 103060000
         */
        CODE = MAJOR_VERSION * MULTIPLIER_MAJOR +
                MINOR_VERSION * MULTIPLIER_MINOR +
                PATCH_VERSION * MULTIPLIER_PATCH +
                COMMIT_COUNT
    }
}

object DependencyType {
    const val IMPLEMENTATION = "implementation"
    const val MOCK_IMPLEMENTATION = "mockImplementation"
    const val KAPT = "kapt"
    const val TEST_IMPLEMENTATION = "testImplementation"
    const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
}

object Plugins {
    const val ANDROID_GRADLE_PLUGIN =
        "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_GRADLE_PLUGIN}"
    const val DETEKT_GRADLE_PLUGIN =
        "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.DETEKT_GRADLE_PLUGIN}"
    const val DETEKT_KTLINT =
        "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.DETEKT_GRADLE_PLUGIN}"
    const val DEPENDENCY_CHECKER_PLUGIN =
        "com.github.ben-manes:gradle-versions-plugin:${Versions.DEPENDENCY_CHECKER_PLUGIN}"
    const val SPOTBUGS_GRADLE_PLUGIN =
        "gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:${Versions.SPOTBUGS_GRADLE_PLUGIN}"
    const val SONARQUBE_GRADLE_PLUGIN =
        "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.SONARQUBE_GRADLE_PLUGIN}"
    const val HILT_GRADLE_PLUGIN =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_GRADLE_PLUGIN}"
    const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN}"

    object Versions {
        const val ANDROID_GRADLE_PLUGIN = "_"
        const val KOTLIN_GRADLE_PLUGIN = "_"
        const val DETEKT_GRADLE_PLUGIN = "_"
        const val DEPENDENCY_CHECKER_PLUGIN = "_"
        const val SPOTBUGS_GRADLE_PLUGIN = "_"
        const val SONARQUBE_GRADLE_PLUGIN = "_"
        const val HILT_GRADLE_PLUGIN = "_"
        const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN = "_"
    }
}

object Libs {
    object Accompanist {
        private const val VERSION = "_"
        const val INSETS = "com.google.accompanist:accompanist-insets:_"
        const val INSETS_UI = "com.google.accompanist:accompanist-insets-ui:_"
        const val SWIPE_REFRESH = "com.google.accompanist:accompanist-swiperefresh:_"
        const val SYSTEM_UI_CONTROLLER =
            "com.google.accompanist:accompanist-systemuicontroller:_"
        const val PLACEHOLDER = "com.google.accompanist:accompanist-placeholder-material:_"
        const val FLOW_LAYOUT = "com.google.accompanist:accompanist-flowlayout:_"
        const val PAGER = "com.google.accompanist:accompanist-pager:_"
        const val PAGER_INDICATORS = "com.google.accompanist:accompanist-pager-indicators:_"
    }

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:_"
        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:_"
        const val CARD_VIEW = "androidx.cardview:cardview:_"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:_"
        const val CORE_KTX = "androidx.core:core-ktx:_"
        const val CORE_SPLASHSCREEN = "androidx.core:core-splashscreen:_"

        object Activity {
            const val VERSION = "_"
            const val ACTIVITY = "androidx.activity:activity:_"
            const val ACTIVITY_KTX = "androidx.activity:activity-ktx:_"
        }

        object Compose {
            const val VERSION = "_"
            private const val MATERIAL3_VERSION = "_"
            const val UI = "androidx.compose.ui:ui:_"
            const val RUNTIME = "androidx.compose.ui:ui:_"
            const val MATERIAL = "androidx.compose.material:material:_"
            const val MATERIAL3 = "androidx.compose.material3:material3:_"
            const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:_"
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling:_"
            const val UI_TEST = "androidx.compose.ui:ui-test-junit4:_"
            const val ACTIVITY = "androidx.activity:activity-compose:${Activity.VERSION}"
            const val HILT = "androidx.hilt:hilt-navigation-compose:_"
            const val ICONS = "androidx.compose.material:material-icons-extended:_"
            const val TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:_"
            const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:_"
            const val PAGING = "androidx.paging:paging-compose:_"
            const val COIL = "io.coil-kt:coil-compose:_"
        }

        object DataStore {
            private const val VERSION = "_"
            const val PREFERENCES = "androidx.datastore:datastore-preferences:_"
        }

        object Fragment {
            private const val VERSION = "_"
            const val FRAGMENT = "androidx.fragment:fragment:_"
            const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:_"
        }

        object Hilt {
            private const val version = "_"
            const val COMPILER = "androidx.hilt:hilt-compiler:_"
        }

        object Lifecycle {
            private const val VERSION = "_"
            const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:_"
            const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:_"
            const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:_"
            const val COMPILER = "androidx.lifecycle:lifecycle-compiler:_"
        }

        object Navigation {
            private const val VERSION = "_"
            const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:_"
            const val UI = "androidx.navigation:navigation-ui-ktx:_"
        }
    }

    object ComposeDestinations {
        private const val VERSION = "_"
        const val CORE = "io.github.raamcosta.compose-destinations:core:_"
        const val KSP = "io.github.raamcosta.compose-destinations:ksp:_"
        const val ANIMATIONS_CORE =
            "io.github.raamcosta.compose-destinations:animations-core:_"
    }

    object Coroutines {
        private const val VERSION = "_"
        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:_"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:_"
        const val DEBUG = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:_"
    }

    object Chucker {
        private const val VERSION = "_"
        const val DEBUG = "com.github.chuckerteam.chucker:library:_"
        const val RELEASE = "com.github.chuckerteam.chucker:library-no-op:_"
    }

    object Google {
        const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

        private object Versions {
            const val GSON = "_"
            const val MATERIAL = "_"
        }
    }

    object Hilt {
        private const val VERSION = "_"
        const val LIBRARY = "com.google.dagger:hilt-android:_"
        const val COMPILER = "com.google.dagger:hilt-android-compiler:_"
        const val TESTING = "com.google.dagger:hilt-android-testing:_"
    }

    object Kotlin {
        private const val VERSION = "_"
        const val STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:_"
    }

    object Moshi {
        private const val VERSION = "_"
        const val MOSHI = "com.squareup.moshi:moshi:_"
        const val ADAPTERS = "com.squareup.moshi:moshi-adapters:_"
        const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:_"
        const val CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:_"
    }

    object OkHttp {
        private const val VERSION = "_"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:_"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:_"
        const val MOCKWS = "com.squareup.okhttp3:mockwebserver:_"
    }

    object Other {
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

        private object Versions {
            const val TIMBER = "_"
        }
    }

    object Retrofit {
        private const val VERSION = "_"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:_"
        const val MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:_"
        const val SCALAR_CONVERTER = "com.squareup.retrofit2:converter-scalars:_"
    }

    object Test {
        const val CORE = "androidx.arch.core:core-testing:${Versions.CORE}"
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val MOCKITO_CORE = "org.mockito:mockito-core:${Versions.MOCKITO_CORE}"
        const val MOCKITO_KOTLIN = "org.mockito.kotlin:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
        const val COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
        const val ANDROID_CORE = "androidx.test:core:${Versions.ANDROID_TEST}"
        const val ANDROID_RUNNER = "androidx.test:runner:${Versions.ANDROID_TEST}"
        const val ANDROID_ESPRESSO_CORE =
            "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
        const val ANDROID_ESPRESSO_CONTRIB =
            "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
        const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"

        private object Versions {
            const val CORE = "_"
            const val JUNIT = "_"
            const val MOCKITO_CORE = "_"
            const val MOCKITO_KOTLIN = "_"
            const val COROUTINES = "_"
            const val ANDROID_TEST = "_"
            const val ESPRESSO = "_"
            const val ANDROID_JUNIT = "_"
        }
    }
}

fun String.execute(workingDir: File = File("./")): String? =
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(parts)
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(Versions.TIMEOUT_MINUTES, TimeUnit.MINUTES)
        proc.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
