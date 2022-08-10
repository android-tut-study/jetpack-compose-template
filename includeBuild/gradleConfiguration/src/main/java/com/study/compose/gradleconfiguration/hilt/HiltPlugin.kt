package com.study.compose.gradleconfiguration.hilt

import com.study.compose.gradleconfiguration.util.debug
import com.study.compose.gradleconfiguration.util.warning
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

const val Default_Hilt_Version = "2.42"
const val Default_Hilt_Navigation_Compose_Version = "1.0.0"

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val hiltExtension =
            target.extensions.create("hiltConfiguration", HiltPluginExtension::class.java)
        val hiltLibs = target.extensions.getByType<VersionCatalogsExtension>().find("hiltLibs")

        target.pluginManager.apply("kotlin-kapt")

        hiltLibs.ifPresentOrElse(
            { hiltLibCatalog ->
                applyHiltCoreDependencies(target, hiltLibCatalog)
            },
            {
                target.pluginManager.apply("dagger.hilt.android.plugin")
                applyHiltDefaultCoreDependencies(target)
            }
        )

        target.afterEvaluate {
            if (hiltExtension.version.isPresent) {
                debug("Forced Apply with [Hilt Extension Version]: ${hiltExtension.version.get()}")
                val hiltVersion = hiltExtension.version.get()
                applyHiltDefaultCoreDependencies(target, hiltVersion)
            }

            if (hiltExtension.navigation.applied.isPresent && hiltExtension.navigation.applied.get()) {
                // Apply navigation dependencies
                if (hiltExtension.navigation.composeVersion.isPresent) {
                    val navigationComposeVersion = hiltExtension.navigation.composeVersion.get()
                    debug("Apply Hilt Navigation Compose from gradle build config $navigationComposeVersion")
                    applyNavigationCompose(target, navigationComposeVersion)
                } else {
                    hiltLibs.ifPresentOrElse(
                        { hiltLibCatalog ->
                            hiltLibCatalog.findLibrary("hilt-navigation-compose").ifPresentOrElse(
                                { target.dependencies.add("implementation", it) },
                                {
                                    debug("Not found [hilt-navigation-compose] Library in version catalog! Apply Default Hilt Navigation Compose")
                                    applyNavigationCompose(
                                        target,
                                        Default_Hilt_Navigation_Compose_Version
                                    )
                                }
                            )
                        },
                        {
                            debug("Not found version catalog config! Apply Default Hilt Navigation Compose")
                            applyNavigationCompose(
                                target,
                                Default_Hilt_Navigation_Compose_Version
                            )
                        }
                    )
                }
            }
        }
    }

    private fun applyNavigationCompose(project: Project, version: String) {
        project.dependencies.add(
            "implementation",
            "androidx.hilt:hilt-navigation-compose:$version"
        )
    }

    private fun applyHiltCoreDependencies(
        target: Project,
        hiltLibs: VersionCatalog
    ) {
        target.debug(
            "Apply Hilt Core Dependencies with VersionCatalog ${
                hiltLibs.findVersion("hilt").get()
            }"
        )
        target.pluginManager.apply {
            hiltLibs.findPlugin("hilt-app").ifPresent { hiltPlugin ->
                apply(hiltPlugin.get().pluginId)
            }
        }

        target.dependencies.apply {
            add("implementation", hiltLibs.findLibrary("hilt-android").get())
            add("kapt", hiltLibs.findLibrary("hilt-android-compiler").get())
        }
    }

    private fun applyHiltDefaultCoreDependencies(target: Project, forcedVersion: String? = null) {
        var resolvedVersion = forcedVersion ?: Default_Hilt_Version
        if (resolvedVersion.toFloat() < Default_Hilt_Version.toFloat()) {
            target.warning("Current Hilt Setting is $forcedVersion")
            target.warning("Please make sure Hilt Version in gradle build larger than $Default_Hilt_Version >>> Apply Default Hilt Version $Default_Hilt_Version")
            resolvedVersion = Default_Hilt_Version
        }
        target.debug("Apply Hilt Dependencies with version $resolvedVersion")
        target.dependencies.apply {
            add("implementation", "com.google.dagger:hilt-android:$resolvedVersion")
            add("kapt", "com.google.dagger:hilt-android-compiler:$resolvedVersion")
        }
    }
}
