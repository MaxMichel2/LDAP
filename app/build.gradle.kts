apply {
    from("../config/quality.gradle")
}

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        applicationId = "com.worldline.bootstrap"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = Versions.CODE
        versionName = Versions.NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "rules/proguard-rules.pro",
            "rules/proguard-rules-dagger.pro",
            "rules/proguard-rules-gson.pro",
            "rules/proguard-rules-okhttp.pro",
            "rules/proguard-rules-retrofit.pro",
            "rules/proguard-rules-timber.pro"
        )

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }


    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    signingConfigs {
        create("config") {
            storeFile = file("../keystore.jks")
            storePassword = "atosatos"
            keyAlias = "key"
            keyPassword = "atosatos"
        }
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("config")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    flavorDimensions.add("env")

    productFlavors {
        create("prod") {
            dimension = "env"
        }
        create("rce") {
            applicationIdSuffix = ".rce"
            dimension = "env"
        }
        create("mock") {
            applicationIdSuffix = ".mock"
            dimension = "env"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.VERSION
    }

    packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/ASL2.0")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("META-INF/*.kotlin_module")
    }
}

val mockImplementation by configurations

dependencies {
    implementation(project(":presentation-compose")) // Switch between presentation and presentation-compose
//    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))
    mockImplementation(project(":data-mock"))
    implementation(project(":common"))

    implementation(Libs.Kotlin.STDLIB)

    implementation(Libs.AndroidX.APP_COMPAT)
    implementation(Libs.AndroidX.Activity.ACTIVITY_KTX)
    implementation(Libs.AndroidX.Fragment.FRAGMENT_KTX)
    implementation(Libs.AndroidX.RECYCLER_VIEW)
    implementation(Libs.AndroidX.CARD_VIEW)
    implementation(Libs.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libs.AndroidX.CORE_KTX)

    implementation(Libs.AndroidX.Lifecycle.VIEWMODEL)
    implementation(Libs.AndroidX.Lifecycle.LIVEDATA)
    implementation(Libs.AndroidX.Lifecycle.RUNTIME)

    implementation(Libs.Google.MATERIAL)

    implementation(Libs.Hilt.LIBRARY)
    kapt(Libs.Hilt.COMPILER)
    kapt(Libs.AndroidX.Hilt.COMPILER)

    implementation(Libs.Moshi.MOSHI)
    implementation(Libs.Moshi.MOSHI_KOTLIN)
    implementation(Libs.Retrofit.RETROFIT)
    debugImplementation(Libs.OkHttp.LOGGING_INTERCEPTOR)

    testImplementation(Libs.Test.CORE)
    testImplementation(Libs.Test.JUNIT)
    testImplementation(Libs.Test.MOCKITO_CORE)
    testImplementation(Libs.Test.MOCKITO_KOTLIN)
    testImplementation(Libs.Test.COROUTINES)

    androidTestImplementation(Libs.Test.ANDROID_CORE)
    androidTestImplementation(Libs.Test.ANDROID_RUNNER)
    androidTestImplementation(Libs.Test.ANDROID_ESPRESSO_CORE)
    androidTestImplementation(Libs.Test.ANDROID_ESPRESSO_CONTRIB)
    androidTestImplementation(Libs.Test.ANDROID_JUNIT)
}
