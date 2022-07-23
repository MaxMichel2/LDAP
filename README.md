# LDAP

## Architecture

### Global

* __Language__: Kotlin
* __Compatibilité__: API 21 (Android 5) or higher
* __UI
  Paradigm__: [MVI](https://medium.com/@VolodymyrSch/android-simple-mvi-implementation-with-jetpack-compose-5ee5d6fc4908) (
  MVVM with SSOT)
* __Global architecture__:
  * [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
  * Single Activity Architecture
  * Modular Architecture: 5 Gradle modules, one for each clean architecture layer + 1 Application
    module + 1 module for mocked data

### Variants

* `env`
  * `prod` - Connected to production server
  * `rce` - Connected to external server
  * `mock` - Local mock
* `buildType`
  * `debug` - Development buildType
  * `release` - For client release

## Environment

### Dependencies

#### XML

- Dependency
  injection: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- Persistence: [Room](https://developer.android.com/topic/libraries/architecture/room)
- Navigation: [Navigation component](https://developer.android.com/guide/navigation)
- Concurrency: [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- Serialization: [Moshi](https://github.com/square/moshi)
- Logs: [Timber](https://github.com/JakeWharton/timber)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata): Build data
  objects that notify views when the underlying database changes
- [AndroidX](https://developer.android.com/jetpack/androidx)
- [Android KTX](https://developer.android.com/kotlin/ktx)
- HTTP Client: [OkHttp](https://square.github.io/okhttp/)
- HTTP API management: [Retrofit](https://square.github.io/retrofit/)

#### Jetpack Compose

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Dependency
  injection: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- Persistence: [Room](https://developer.android.com/topic/libraries/architecture/room)
- Navigation: [Compose destinations](https://composedestinations.rafaelcosta.xyz/)
- [Accompanist](https://google.github.io/accompanist/): Jetpack Compose QoL library
- Concurrency: [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- Serialization: [Moshi](https://github.com/square/moshi)
- Logs: [Timber](https://github.com/JakeWharton/timber)
- [Flows](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow): Type that can emit
  multiple values sequentially, as opposed to suspend functions that return only a single value
- [AndroidX](https://developer.android.com/jetpack/androidx)
- [Android KTX](https://developer.android.com/kotlin/ktx)
- HTTP Client: [OkHttp](https://square.github.io/okhttp/)
- HTTP API management: [Retrofit](https://square.github.io/retrofit/)

#### Version checking

- [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin): Check for
  dependencies that can be upgraded (Also checks Gradle itself)
- [refreshVersions](https://jmfayard.github.io/refreshVersions/): *Life is too short to google for
  dependencies and versions*

### Tests

- Instrumented tests: [Espresso](https://developer.android.com/training/testing/espresso)
- Unit
  tests: [Mockito](https://developer.android.com/training/testing/unit-testing/local-unit-tests)

### Quality

* Lint
* Detekt
* Ktlint
* Sonarqube
* Spotbugs

All quality checks are automatically launched by Gitlab-CI

### Delivery

TODO

### Delivery Checklist

> Pour suivre l'avancement de la livraison, une issue respectant le template `checklist_delivery` peut être créée [ici](https://gitlab.kazan.myworldline.com/mobile-tools/bootstrap-mvvm/-/issues/new)

