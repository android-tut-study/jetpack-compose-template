package com.study.compose.gradleconfiguration.hilt

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

abstract class HiltNavigation {

    @get:Input
    abstract val composeVersion: Property<String>

}