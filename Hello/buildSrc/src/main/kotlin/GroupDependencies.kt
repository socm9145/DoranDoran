val composeDependencies = listOf(
    Compose.UI,
    Compose.PREVIEW,
    Compose.MATERIAL3,
)

val composeDebug = listOf(
    Debug.COMPOSE_TOOLING,
    Debug.COMPOSE_TEST_MANIFEST,
)

val imageLoadDependencies = listOf(
    Coil.COMPOSE
)

val appDependencies = listOf(
    KTX.CORE,
    KTX.LIFECYCLE_RUNTIME,
    Compose.ACTIVITY,
)

val hiltDependencies = listOf(
    Hilt.HILT_NAV_COMPOSE
)

val androidLibraryDependencies = listOf(
    KTX.CORE,
)

val defaultUnitTest = listOf(
    UnitTest.JUNIT,
)

val defaultAndroidTest = listOf(
    AndroidTest.ESPRESSO_CORE,
    AndroidTest.ANDROID_JUNIT,
)
