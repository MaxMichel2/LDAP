repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(gradleApi())
    implementation(Android.tools.build.gradlePlugin)
    implementation(kotlin("gradle-plugin", "1.7.0"))
    // Required due to version issues with Dagger/Hilt and AGP
    implementation("com.squareup:javapoet:_")
}

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("module-plugin") {
            id = "module-plugin"
            implementationClass = "CommonModulePlugin"
        }
    }
}