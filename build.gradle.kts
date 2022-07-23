// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.CONFIGURATION_DETEKT_PLUGINS
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(Plugins.ANDROID_GRADLE_PLUGIN)
        classpath(Plugins.KOTLIN_GRADLE_PLUGIN)
        classpath(Plugins.DETEKT_GRADLE_PLUGIN)
        classpath(Plugins.DEPENDENCY_CHECKER_PLUGIN)
        classpath(Plugins.SPOTBUGS_GRADLE_PLUGIN)
        classpath(Plugins.SONARQUBE_GRADLE_PLUGIN)
        classpath(Plugins.HILT_GRADLE_PLUGIN)
        classpath(Plugins.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
    configurations.all {
        resolutionStrategy {
            cacheChangingModulesFor(0, "seconds")
            exclude(
                group = "org.jetbrains.kotlinx",
                module = "kotlinx-coroutines-debug"
            )
        }
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "com.github.spotbugs")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "org.sonarqube")

    configure<DetektExtension> {
        toolVersion = Plugins.Versions.DETEKT_GRADLE_PLUGIN
        source = files("$projectDir/src")
        config = files("$rootDir/config/quality/detekt/detekt-config.yml")
        baseline = file("$rootDir/config/quality/detekt/baseline.xml")
        autoCorrect = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
        exclude("**/test/")
        exclude(".*/tmp/.*")
    }

    dependencies {
        CONFIGURATION_DETEKT_PLUGINS(Plugins.DETEKT_KTLINT)
    }
}
