plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.purple.core.designsystem"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
    implementation(composeDependencies)
    debugImplementation(composeDebug)

    implementation(androidLibraryDependencies)
    testImplementation(defaultUnitTest)
    androidTestImplementation(defaultAndroidTest)

    /*
    * TODO : 외부 라이브러리 쓰기로 확정 시, gradle 정리 할 것 !
    * */
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.1.1")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.1.1")
}
