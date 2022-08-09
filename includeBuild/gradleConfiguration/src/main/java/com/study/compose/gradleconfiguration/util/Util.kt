package com.study.compose.gradleconfiguration.util

import org.gradle.api.Project

fun Project.warning(msg: String) = logger.warn(msg)
fun Project.debug(msg: String) = logger.debug(msg)
fun Project.quite(msg: String, vararg obj: Any) = logger.quiet(msg, obj)