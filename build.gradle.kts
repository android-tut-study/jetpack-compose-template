@Suppress("DSL_SCOPE_VIOLATION") // bug at https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(gradlePlugins.plugins.application).apply(false)
    alias(gradlePlugins.plugins.library).apply(false)
    alias(gradlePlugins.plugins.kotlin).apply(false)
    alias(gradlePlugins.plugins.hilt).apply(false)
    alias(gradlePlugins.plugins.ktlint)
    alias(gradlePlugins.plugins.detekt)
    checkstyle
}

tasks.register("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    apply {
        plugin(rootProject.gradlePlugins.plugins.ktlint.get().pluginId)
        plugin(rootProject.gradlePlugins.plugins.detekt.get().pluginId)
    }

    detekt {
        config = rootProject.files("./config/detekt/detekt.yml")
    }
}

checkstyle {
    // will use the version declared in the catalog
    toolVersion = gradlePlugins.versions.checkstyle.get()
}
apply(from = "gradle/projectDependencyGraph.gradle")
