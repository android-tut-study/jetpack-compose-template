package com.study.compose.gradleconfiguration

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

open class TestFixtureExtension constructor(private val project: Project) {

    fun applyTestFixture(enableAndroidResource: Boolean = false) {
        project.extensions.configure(LibraryExtension::class.java) {
            testFixtures {
                enable = true
                androidResources = enableAndroidResource
            }
        }
    }
}