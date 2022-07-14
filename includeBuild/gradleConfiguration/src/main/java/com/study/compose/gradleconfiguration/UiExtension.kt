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
}