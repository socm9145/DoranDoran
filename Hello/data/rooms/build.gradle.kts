plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(Kotlin.KOTLIN_COROUTINES)
    implementation(retrofit2Dependencies)
}
