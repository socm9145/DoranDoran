plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.purple.core.database"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(Room.ROOM)
    implementation(Room.ROOM_KTX)
    implementation(Room.ROOM_COMMON)
    implementation(Kotlin.KOTLIN_COROUTINES)
    annotationProcessor(Room.ROOM_COMPILER)
    kapt(Room.ROOM_COMPILER)

    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)
}
