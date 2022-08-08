package com.study.compose.gradleconfiguration.util

import org.gradle.api.Project

fun Project.warning(msg: String) = logger.warn(msg)
fun Project.debug(msg: String) = logger.debug(msg)