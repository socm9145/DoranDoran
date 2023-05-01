object Versions {

    // Ktx
    const val CORE = "1.9.0"
    const val LIFECYCLE_RUNTIME = "2.6.1"

    // Compose
    const val COMPOSE = "1.3.3"
    const val MATERIAL3 = "1.0.1"
    const val ACTIVITY = "1.7.0"

    // Coil
    const val COIL = "2.3.0"

    // Test
    const val JUNIT = "4.13.2"

    // Android Test
    const val ESPRESSO_CORE = "3.5.1"
    const val ANDROID_JUNIT = "1.1.5"

    // Hilt
    const val HILT_ANDROID = "2.44"
    const val HILT_COMPOSE = "1.0.0"

    // Kotlin
    const val KOTLIN = "1.8.10"
    const val KOTLIN_COROUTINES = "1.6.4"

    // Calendar Library
    const val CALENDAR_LIBRARY = "1.1.1"

    // Social Login
    const val GOOGLE_LOGIN = "20.5.0"
    const val KAKAO_LOGIN = "2.13.0"

    // Retrofit2
    const val RETROFIT2 = "2.9.0"
    const val RETROFIT2_KOTLINX_SERIALIZATION = "1.0.0"

    // Room
    const val ROOM = "2.5.0"

    // Kotlinx Serialization Json
    const val KOTLIN_SELIAIZATION_JSON = "1.5.0"
}

object KTX {
    const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME}"
}

object Compose {
    const val UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
    const val MATERIAL3 = "androidx.compose.material3:material3:${Versions.MATERIAL3}"
    const val ACTIVITY = "androidx.activity:activity-compose:${Versions.ACTIVITY}"
}

object Coil {
    const val COMPOSE = "io.coil-kt:coil-compose:${Versions.COIL}"
}

object UnitTest {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}

object AndroidTest {
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val COMPOSE_JUNIT = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

object Debug {
    const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val COMPOSE_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"
}

object Hilt {
    const val HILT_NAV_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMPOSE}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT_ANDROID}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT_ANDROID}"
}

object Kotlin {
    const val KOTLIN_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}"
}

object CalendarLibrary {
    const val CALENDAR_CORE = "com.maxkeppeler.sheets-compose-dialogs:core:${Versions.CALENDAR_LIBRARY}"
    const val CALENDAR = "com.maxkeppeler.sheets-compose-dialogs:calendar:${Versions.CALENDAR_LIBRARY}"
}
object SocialLogin {
    const val GOOGLE_LOGIN = "com.google.android.gms:play-services-auth:${Versions.GOOGLE_LOGIN}"
    const val KAKAO_LOGIN = "com.kakao.sdk:v2-user:${Versions.KAKAO_LOGIN}"
}

object Retrofit2 {
    const val RETROFIT2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val KOTLIN_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.RETROFIT2_KOTLINX_SERIALIZATION}"
}

object Room {
    const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_COMMON = "androidx.room:room-common:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

object KotlinxSerializationJson {
    const val KOTLIN_SELIAIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SELIAIZATION_JSON}"
}
