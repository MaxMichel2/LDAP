import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Common module plugin that is applied to all android library modules.
 *
 * This gradle plugin is pre-applied to the `build.gradle.kts` file.
 */
class CommonModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Apply plugins common to all projects
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-parcelize")
        project.plugins.apply("kotlin-kapt")
        project.plugins.apply("dagger.hilt.android.plugin")

        // Configure the android block
        val androidExtensions = project.extensions.getByName("android")
        if (androidExtensions is BaseExtension) {
            androidExtensions.apply {
                compileSdkVersion(Versions.COMPILE_SDK)

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                project.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = JavaVersion.VERSION_11.toString()
                    }
                }

                testOptions {
                    unitTests.isReturnDefaultValues = true
                }

                defaultConfig {
                    minSdk = Versions.MIN_SDK
                    targetSdk = Versions.TARGET_SDK
                    versionCode = Versions.CODE
                    versionName = Versions.NAME

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables.useSupportLibrary = true
                }

                buildFeatures.viewBinding = true

                when (this) {
                    is LibraryExtension -> {
                        defaultConfig {
                            // Apply the pro guard rules for library
                            consumerProguardFiles("consumer-rules.pro")
                        }
                    }

                    is AppExtension -> {
                        buildTypes {
                            getByName("release") {
                                isMinifyEnabled = false
                                proguardFiles(
                                    getDefaultProguardFile("proguard-android-optimize.txt"),
                                    "proguard-rules.pro"
                                )
                            }
                        }
                    }
                }

                flavorDimensionList.add("env")

                productFlavors {
                    create("prod") {
                        dimension = "env"
                    }
                    create("rce") {
                        dimension = "env"
                    }
                    create("mock") {
                        dimension = "env"
                    }
                }
            }
        }

        // Dependencies common to all projects
        project.dependencies {
            add(DependencyType.IMPLEMENTATION, Libs.Kotlin.STDLIB)
            add(DependencyType.IMPLEMENTATION, Libs.AndroidX.CORE_KTX)
            add(DependencyType.IMPLEMENTATION, Libs.AndroidX.APP_COMPAT)

            add(DependencyType.IMPLEMENTATION, Libs.Hilt.LIBRARY)
            add(DependencyType.KAPT, Libs.Hilt.COMPILER)
            add(DependencyType.KAPT, Libs.AndroidX.Hilt.COMPILER)


            add(DependencyType.TEST_IMPLEMENTATION, Libs.Test.CORE)
            add(DependencyType.TEST_IMPLEMENTATION, Libs.Test.JUNIT)
            add(DependencyType.TEST_IMPLEMENTATION, Libs.Test.MOCKITO_CORE)
            add(DependencyType.TEST_IMPLEMENTATION, Libs.Test.MOCKITO_KOTLIN)
            add(DependencyType.TEST_IMPLEMENTATION, Libs.Test.COROUTINES)

            add(DependencyType.ANDROID_TEST_IMPLEMENTATION, Libs.Test.ANDROID_CORE)
            add(DependencyType.ANDROID_TEST_IMPLEMENTATION, Libs.Test.ANDROID_RUNNER)
            add(DependencyType.ANDROID_TEST_IMPLEMENTATION, Libs.Test.ANDROID_ESPRESSO_CORE)
            add(DependencyType.ANDROID_TEST_IMPLEMENTATION, Libs.Test.ANDROID_ESPRESSO_CONTRIB)
            add(DependencyType.ANDROID_TEST_IMPLEMENTATION, Libs.Test.ANDROID_JUNIT)
        }
    }
}