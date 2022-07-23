apply {
    from("../config/quality.gradle")
}

plugins {
    id("module-plugin")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(Libs.Retrofit.RETROFIT)
    implementation(Libs.Retrofit.MOSHI_CONVERTER)
    implementation(Libs.Retrofit.SCALAR_CONVERTER)

    implementation(Libs.OkHttp.OKHTTP)
    debugImplementation(Libs.OkHttp.LOGGING_INTERCEPTOR)

    implementation(Libs.Moshi.MOSHI)
    implementation(Libs.Moshi.MOSHI_KOTLIN)
    implementation(Libs.Moshi.ADAPTERS)
    kapt(Libs.Moshi.CODEGEN)

    implementation(Libs.AndroidX.DataStore.PREFERENCES)

    debugImplementation(Libs.Chucker.DEBUG)
    releaseImplementation(Libs.Chucker.RELEASE)
}
