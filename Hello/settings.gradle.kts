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
include(":core:data")
include(":feature:rooms")
include(":domain:rooms")

include(":core:model")
include(":domain:model")
include(":domain:model")
include(":data:account")
include(":data:user")
include(":core:network")
include(":domain:repo")
include(":domain:account")
include(":core:datastore")
