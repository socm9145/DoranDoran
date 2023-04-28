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

val calendarLibrary = listOf(
    CalendarLibrary.CALENDAR_CORE,
    CalendarLibrary.CALENDAR,
)

val socialLogin = listOf(
    SociaLogin.GOOGLE_LOGIN,
    SociaLogin.KAKAO_LOGIN,
)

val retrofit2Dependencies = listOf(
    Retrofit2.RETROFIT2,
    Retrofit2.KOTLIN_CONVERTER,
)

val protobufDependencies = listOf(
    Protobuf.PROTOBUF_JAVALITE,
    Protobuf.PROTOBUF_KOTLINLITE,
)
