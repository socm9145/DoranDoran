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
include(":core:designsystem")
include(":core:ui")
<<<<<<< HEAD
include(":feature:login")
=======
include(":feature:rooms")
include(":domain:rooms")
>>>>>>> e3f9b6076c91b3c6ac7717f3e2625bdac286b570
