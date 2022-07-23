apply {
    from("../config/quality.gradle")
}

plugins {
    id("module-plugin")
    id("androidx.navigation.safeargs")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(Libs.AndroidX.APP_COMPAT)
    implementation(Libs.AndroidX.Activity.ACTIVITY_KTX)
    implementation(Libs.AndroidX.Fragment.FRAGMENT_KTX)
    implementation(Libs.AndroidX.RECYCLER_VIEW)
    implementation(Libs.AndroidX.CARD_VIEW)
    implementation(Libs.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.CORE_SPLASHSCREEN)

    implementation(Libs.AndroidX.Lifecycle.VIEWMODEL)
    implementation(Libs.AndroidX.Lifecycle.LIVEDATA)
    implementation(Libs.AndroidX.Lifecycle.RUNTIME)

    implementation(Libs.Google.MATERIAL)

    kapt(Libs.AndroidX.Lifecycle.COMPILER)

    implementation(Libs.AndroidX.Navigation.FRAGMENT_KTX)
    implementation(Libs.AndroidX.Navigation.UI)
}
