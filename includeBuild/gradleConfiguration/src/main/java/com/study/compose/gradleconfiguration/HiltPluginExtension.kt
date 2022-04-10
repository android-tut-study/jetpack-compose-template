package com.study.compose.gradleconfiguration

import org.gradle.api.Project

open class HiltPluginExtension constructor(private val project: Project) {

    fun addNavigationDependency() {
        project.dependencies.apply {
            add("implementation", "androidx.hilt:hilt-navigation-compose:1.0.0")
        }
    }

}