package com.study.compose.gradleconfiguration.app.ext

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

const val Default_Compose_Version = "1.3.0-alpha01"
const val Default_Compose_Compiler_Extension_Version = "1.2.0"

abstract class UiExtension constructor(private val project: Project) {

    fun applyCompose(forcedVersion: String? = null) {
        val androidxCatalogExtension =
            project.extensions.getByType<VersionCatalogsExtension>().find("androidxLibs")
        var composeCompilerExtensionVersion = Default_Compose_Compiler_Extension_Version
        if (forcedVersion != null) {
            composeCompilerExtensionVersion = forcedVersion
        } else if (androidxCatalogExtension.isPresent && androidxCatalogExtension.get()
                .findVersion("compose-compiler-extension").isPresent
        ) {
            composeCompilerExtensionVersion =
                androidxCatalogExtension.get().findVersion("compose-compiler-extension").get()
                    .toString()
        }

        project.extensions.configure<BaseExtension> {
            buildFeatures.apply {
                viewBinding = true
                compose = true
            }

            println("Apply Compose Extension $composeCompilerExtensionVersion")
            composeOptions {
                kotlinCompilerExtensionVersion = composeCompilerExtensionVersion
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
