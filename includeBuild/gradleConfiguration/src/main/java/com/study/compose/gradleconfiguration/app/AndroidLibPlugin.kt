package com.study.compose.gradleconfiguration.app

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.android.library")
        target.pluginManager.apply(BaseAppPlugin::class.java)
    }
}