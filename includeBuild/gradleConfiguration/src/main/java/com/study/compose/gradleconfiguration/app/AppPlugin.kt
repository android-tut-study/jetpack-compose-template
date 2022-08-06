package com.study.compose.gradleconfiguration.app

import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.android.application")
        target.pluginManager.apply("kotlin-kapt")
        target.pluginManager.apply(BaseAppPlugin::class.java)

        target.extensions.configure(com.android.build.gradle.AppExtension::class.java) {
            compileSdkVersion(32)
            defaultConfig {
                applicationId = "com.study.compose.shrine"
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables.useSupportLibrary = true
            }

            buildTypes {
                getByName("release") {
                    minifyEnabled(false)
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
            packagingOptions {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }
}
