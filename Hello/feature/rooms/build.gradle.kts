plugins {
    kotlin("android")
    id("com.android.library")
}
android {
    namespace = "com.purple.hello.feature.groups"
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

    implementation(project(":core:designsystem"))
    implementation(project(":domain:rooms"))

    implementation(composeDependencies)
    implementation(imageLoadDependencies)
    implementation(androidLibraryDependencies)

    debugImplementation(composeDebug)
    testImplementation(defaultUnitTest)
    androidTestImplementation(defaultAndroidTest)
}
