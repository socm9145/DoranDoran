pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Hello"
include(":app")
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":feature:rooms")
include(":domain:rooms")

include(":core:model")
