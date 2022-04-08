package com.example

object GradlePlugins {

    object Plugins {
        object Hilt {
            const val PLUGIN = "dagger.hilt.android.plugin"
            const val GRADLE = "com.google.dagger.hilt.android"
        }

        object Android {
            const val APPLICATION = "com.android.application"
            const val LIBRARY = "com.android.library"
        }

        object Kotlin {
            const val ANDROID = "android"
            const val KAPT = "kapt"
        }

        object Navigation {
            const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
        }

        object Jetbrains {
            object Kotlin {
                const val ANDROID = "org.jetbrains.kotlin.android"
            }
        }

        object Google {
            const val PROTOBUF = "com.google.protobuf"
        }
    }
}