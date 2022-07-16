@Suppress("DSL_SCOPE_VIOLATION") // bug at https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(gradlePlugins.plugins.application).apply(false)
    alias(gradlePlugins.plugins.library).apply(false)
    alias(gradlePlugins.plugins.kotlin).apply(false)
    alias(gradlePlugins.plugins.hilt).apply(false)
    alias(gradlePlugins.plugins.ktlint)
    alias(gradlePlugins.plugins.detekt)
    alias(gradlePlugins.plugins.kover)
    checkstyle
}

kover {
//    isDisabled = false                      // true to disable instrumentation of all test tasks in all projects
//    generateReportOnCheck = true            // false to do not execute `koverMergedReport` task before `check` task
//    disabledProjects = setOf()              // setOf("project-name") or setOf(":project-name") to disable coverage for project with path `:project-name` (`:` for the root project)
//    instrumentAndroidPackage = false        // true to instrument packages `android.*` and `com.android.*`
    runAllTestsForProjectTask = true       // true to run all tests in all projects if `koverHtmlReport`, `koverXmlReport`, `koverReport`, `koverVerify` or `check` tasks executed on some project
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

tasks.withType<Test>() {
    extensions.configure<kotlinx.kover.api.KoverTaskExtension>() {
        isDisabled = false
        binaryReportFile.set(file("$buildDir/custom/result.bin"))
//        includes = listOf("com.example.*")
//        excludes = listOf("com.example.subpackage.*")
    }
}

apply(plugin = "kover")
apply(from = "gradle/projectDependencyGraph.gradle")
