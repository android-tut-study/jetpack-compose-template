package com.study.compose.gradleconfiguration

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.android.library")
        target.pluginManager.apply("org.jetbrains.kotlin.android")
        target.extensions.create("uiConfiguration", UiExtension::class.java, target)

        target.extensions.configure(BaseExtension::class.java) {
            compileSdkVersion(32)
            defaultConfig {
                minSdk = 21
                targetSdk = 32
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.1.1"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

        }
    }
}