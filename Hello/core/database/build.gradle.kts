plugins {
    id("java-library")
    kotlin("kapt")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Room.ROOM)
    implementation(Room.ROOM_KTX)
    implementation(Room.ROOM_COMMON)
    implementation(Kotlin.KOTLIN_COROUTINES)

    annotationProcessor(Room.ROOM_COMPILER)
    kapt(Room.ROOM_COMPILER)
}
