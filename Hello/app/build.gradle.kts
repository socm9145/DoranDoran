plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.purple.hello"
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "com.purple.hello"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = KotlinOptions.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ComposeOptions.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation(project(":feature:rooms"))

    implementation(composeDependencies)
    implementation(appDependencies)
    implementation(hiltDependencies)

    debugImplementation(composeDebug)
    testImplementation(defaultUnitTest)
    androidTestImplementation(defaultAndroidTest)

    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)
}
