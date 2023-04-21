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
    const val HILT = "1.0.0"

    // Calendar Library
    const val CALENDAR_LIBRARY = "1.1.1"

    // Social Login
    const val GOOGLE_LOGIN = "20.5.0"
    const val KAKAO_LOGIN = "2.13.0"
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
    const val HILT_NAV_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT}"
}

object CalendarLibrary {
    const val CALENDAR_CORE = "com.maxkeppeler.sheets-compose-dialogs:core:${Versions.CALENDAR_LIBRARY}"
    const val CALENDAR = "com.maxkeppeler.sheets-compose-dialogs:calendar:${Versions.CALENDAR_LIBRARY}"
}
object SociaLogin {
    const val GOOGLE_LOGIN = "com.google.android.gms:play-services-auth:${Versions.GOOGLE_LOGIN}"
    const val KAKAO_LOGIN = "com.kakao.sdk:v2-user:${Versions.KAKAO_LOGIN}"
}
