package com.study.compose.gradleconfiguration

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

open class UiExtension constructor(private val project: Project) {

    fun applyCompose() {
        project.extensions.configure(BaseExtension::class.java) {
            buildFeatures.apply {
                compose = true
                viewBinding = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.2.0"
            }
        }
    }

    fun applyUiTest() {
        project.extensions.configure(BaseExtension::class.java) {
            project.dependencies.apply {
                add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:1.1.1")
                add("androidTestImplementation", "androidx.test.ext:junit:1.1.3")
            }
        }
    }
}
