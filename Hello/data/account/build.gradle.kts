plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":core:network"))

    implementation("javax.inject:javax.inject:1")
    implementation(Kotlin.KOTLIN_COROUTINES)
}
