package com.study.compose.gradleconfiguration

import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("dagger.hilt.android.plugin")
        target.pluginManager.apply("kotlin-kapt")
        target.extensions.create("hiltConfiguration", HiltPluginExtension::class.java, target)


        target.dependencies.apply {
            add("implementation", "com.google.dagger:hilt-android:2.41")
            add("kapt", "com.google.dagger:hilt-android-compiler:2.41")
        }
    }
}