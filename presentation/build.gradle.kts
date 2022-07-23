apply {
    from("../config/quality.gradle")
}

plugins {
    id("module-plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp").version("1.6.21-1.0.6")
}

android {
    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.VERSION
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    libraryVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(Libs.AndroidX.APP_COMPAT)
    implementation(Libs.AndroidX.CORE_KTX)
    kapt(Libs.AndroidX.Hilt.COMPILER)
    implementation(Libs.AndroidX.CORE_SPLASHSCREEN)

    // Compose
    implementation(Libs.AndroidX.Compose.ACTIVITY)
    implementation(Libs.AndroidX.Compose.RUNTIME)
    implementation(Libs.AndroidX.Compose.UI)
    implementation(Libs.AndroidX.Compose.MATERIAL3)
    implementation(Libs.AndroidX.Compose.UI_TOOLING)
    implementation(Libs.AndroidX.Compose.UI_TOOLING_PREVIEW)
    implementation(Libs.AndroidX.Compose.HILT)
    implementation(Libs.AndroidX.Compose.ICONS)
    implementation(Libs.AndroidX.Compose.CONSTRAINT_LAYOUT)
    implementation(Libs.AndroidX.Compose.PAGING)

    // Required currently for Compose Previews
    debugImplementation(AndroidX.customView)
    debugImplementation(AndroidX.customView.poolingContainer)

    implementation(Libs.Accompanist.INSETS)
    implementation(Libs.Accompanist.INSETS_UI)
    implementation(Libs.Accompanist.SWIPE_REFRESH)
    implementation(Libs.Accompanist.SYSTEM_UI_CONTROLLER)
    implementation(Libs.Accompanist.FLOW_LAYOUT)
    implementation(Libs.Accompanist.PLACEHOLDER)
    implementation(Libs.Accompanist.PAGER)
    implementation(Libs.Accompanist.PAGER_INDICATORS)

    implementation(Libs.ComposeDestinations.ANIMATIONS_CORE)
    ksp(Libs.ComposeDestinations.KSP)
}