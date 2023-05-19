plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.purple.hello.domain.rooms"
    compileSdk = AppConfig.compileSdk
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":data:rooms"))

    implementation("javax.inject:javax.inject:1")
    implementation(Kotlin.KOTLIN_COROUTINES)

    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)
}
