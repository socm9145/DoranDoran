import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addCommonComposeDependencies() {
    composeDependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.addAppDependencies() {
    appDependencies.forEach {
        add("implementation", it)
    }
}
