package com.study.compose.gradleconfiguration.app

import com.android.build.gradle.BaseExtension
import com.study.compose.gradleconfiguration.app.ext.LibraryConfigurationExtension
import com.study.compose.gradleconfiguration.app.ext.UiExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.withType

class BaseAppPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.android")

        target.extensions.create("uiConfiguration", UiExtension::class.java, target)
        target.extensions.create("libraryConfiguration", LibraryConfigurationExtension::class.java, target)

        target.extensions.configure(BaseExtension::class.java) {
            compileSdkVersion(32)
            defaultConfig {
                minSdk = 21
                targetSdk = 32
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }

        target.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }

        target.dependencies.apply {
            add("testImplementation", "junit:junit:4.13.2")
        }
    }
}