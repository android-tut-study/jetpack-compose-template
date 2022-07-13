@Suppress("DSL_SCOPE_VIOLATION") // bug at https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(gradlePlugins.plugins.application).apply(false)
    alias(gradlePlugins.plugins.library).apply(false)
    alias(gradlePlugins.plugins.kotlin).apply(false)
    alias(gradlePlugins.plugins.hilt).apply(false)
    checkstyle
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
    }
}

tasks.register("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}

checkstyle {
    // will use the version declared in the catalog
    toolVersion = androidx.versions.checkstyle.get()
}
apply(from = "gradle/projectDependencyGraph.gradle")
