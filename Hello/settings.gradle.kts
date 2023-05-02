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
include(":core:model")
include(":core:database")
include(":core:network")
include(":core:datastore")
// feature
include(":feature:rooms")
include(":feature:setting:app")
// domain
include(":domain:rooms")
include(":domain:account")
include(":domain:model")
// data
include(":data:rooms")
include(":data:user")
include(":data:account")
