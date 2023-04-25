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
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "Hello"
include(":app")
// core
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":core:model")
// feature
include(":feature:rooms")
// domain
include(":domain:rooms")
// data
include(":data:rooms")
