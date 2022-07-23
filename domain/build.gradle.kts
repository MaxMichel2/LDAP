apply {
    from("../config/quality.gradle")
}

plugins {
    id("module-plugin")
}

dependencies {
    implementation(project(":common"))
}
