apply {
    from("../config/quality.gradle")
}

plugins {
    id("module-plugin")
}

dependencies {
    api(Libs.Other.TIMBER)
    api(Libs.Coroutines.ANDROID)
    api(Libs.Coroutines.CORE)
    api(Libs.Coroutines.DEBUG)
}
