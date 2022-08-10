package com.study.compose.gradleconfiguration.util

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.androidCoreModule(moduleNotation: String) =
    project(":android-core:android-core-$moduleNotation")

fun DependencyHandler.uiModule(moduleNotation: String) = project(":ui:ui-$moduleNotation")

fun DependencyHandler.coreModule(moduleNotation: String) =
    project(":core:core-$moduleNotation")

fun DependencyHandler.useCaseModule(moduleNotation: String) =
    project(":usecase:usecase-$moduleNotation")

fun DependencyHandler.domainModule(moduleNotation: String) =
    project(":domain:domain-$moduleNotation")