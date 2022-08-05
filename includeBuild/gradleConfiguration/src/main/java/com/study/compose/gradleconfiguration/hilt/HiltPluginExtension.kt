package com.study.compose.gradleconfiguration.hilt

import org.gradle.api.Action
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional

abstract class HiltPluginExtension {

    @get:Optional
    @get:Nested
    abstract val version: Property<String>

    @get:Nested
    abstract val navigation: HiltNavigation

    init {
        version.set("2.42")
    }

    fun navigation(action: Action<HiltNavigation>) {
        action.execute(navigation)
    }
}
