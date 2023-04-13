internal val composeDependencies = listOf(
    Compose.UI,
    Compose.PREVIEW,
    Compose.MATERIAL3,
    AndroidTest.COMPOSE_JUNIT,
    Debug.COMPOSE_TOOLING,
    Debug.COMPOSE_TEST_MANIFEST
)

internal val appDependencies = listOf(
    KTX.CORE,
    KTX.LIFECYCLE_RUNTIME,
    Compose.ACTIVITY,
    UnitTest.JUNIT,
    AndroidTest.ESPRESSO_CORE
)