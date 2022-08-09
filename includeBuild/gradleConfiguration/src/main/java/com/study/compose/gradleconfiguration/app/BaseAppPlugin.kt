package com.study.compose.gradleconfiguration.app

import com.android.build.gradle.BaseExtension
import com.study.compose.gradleconfiguration.app.ext.LibraryConfigurationExtension
import com.study.compose.gradleconfiguration.app.ext.UiExtension
import com.study.compose.gradleconfiguration.util.quite
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import java.util.*

const val Default_Min_SDK = 21
const val Default_Target_SDK = 32
const val Default_Compile_SDK = 32
const val Default_Test_Instrumentation_Runner = "androidx.test.runner.AndroidJUnitRunner"

class BaseAppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.android")
        target.extensions.create("uiConfiguration", UiExtension::class.java, target)
        target.extensions.create(
            "libraryConfiguration",
            LibraryConfigurationExtension::class.java,
            target
        )
        // Apply Android defaultConfig
        applyDefaultConfig(target)

        target.dependencies.apply {
            add("testImplementation", "junit:junit:4.13.2")
        }

        target.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }

    private fun applyDefaultConfig(project: Project) {
        val configProperties = Properties()
        val configPropertyFile = project.rootProject.file("./gradle/config/app.properties")
        var targetSdk = Default_Target_SDK
        var minSdk = Default_Min_SDK
        var compileSdk = Default_Compile_SDK
        var testInstrumentationRunner = Default_Test_Instrumentation_Runner
        if (configPropertyFile.exists()) {
            configProperties.load(configPropertyFile.inputStream())
            configProperties.getProperty("minSdk")?.let { minSdk = it.toInt() }
            configProperties.getProperty("targetSdk")?.let { targetSdk = it.toInt() }
            configProperties.getProperty("compileSdk")?.let { compileSdk = it.toInt() }
            configProperties.getProperty("testInstrumentationRunner")
                ?.let { testInstrumentationRunner = it }
        }

        project.extensions.configure<BaseExtension> {
            project.quite(
                """ 
                    Applying Android default config:
                        compileSdk = $compileSdk
                        minSdk = $minSdk
                        targetSdk = $targetSdk
                        testInstrumentationRunner = $testInstrumentationRunner
                      """.trimIndent()
            )
            compileSdkVersion(compileSdk)
            defaultConfig {
                this.minSdk = minSdk
                this.targetSdk = targetSdk
                this.testInstrumentationRunner = testInstrumentationRunner
                consumerProguardFiles("consumer-rules.pro")
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

            packagingOptions {
                resources {
                    excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                }
            }
        }
    }
}