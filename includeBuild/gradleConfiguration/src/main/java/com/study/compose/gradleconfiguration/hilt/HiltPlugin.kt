package com.study.compose.gradleconfiguration.hilt

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("dagger.hilt.android.plugin")
        target.pluginManager.apply("kotlin-kapt")
        val hiltExtension =
            target.extensions.create("hiltConfiguration", HiltPluginExtension::class.java)

        target.tasks.withType<KotlinCompile>() {
            if (hiltExtension.version.isPresent) {
                val version = hiltExtension.version.get()
                target.dependencies.apply {
                    add("implementation", "com.google.dagger:hilt-android:$version")
                    add("kapt", "com.google.dagger:hilt-android-compiler:$version")

                    if (hiltExtension.navigation.composeVersion.isPresent) {
                        val navigationComposeVersion = hiltExtension.navigation.composeVersion.get()
                        add(
                            "implementation",
                            "androidx.hilt:hilt-navigation-compose:$navigationComposeVersion"
                        )
                    }
                }
            }
        }
    }
}
