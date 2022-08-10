@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`.version("2.1.7")
    checkstyle
    alias(gradlePlugins.plugins.ktlint)
    alias(gradlePlugins.plugins.detekt)
}

checkstyle {
    // will use the version declared in the catalog
    toolVersion = gradlePlugins.versions.checkstyle.get()
}

allprojects {
    apply {
        plugin(rootProject.gradlePlugins.plugins.ktlint.get().pluginId)
        plugin(rootProject.gradlePlugins.plugins.detekt.get().pluginId)
    }

    detekt {
        config = rootProject.files("../../config/detekt/detekt.yml")
    }
}

repositories {
    google()
    mavenCentral()
    jcenter() // Warning: this repository is going to shut down soon
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("app-plugin") {
        id = "app-plugin"
        implementationClass = "com.study.compose.gradleconfiguration.app.AppPlugin"
    }

    plugins.register("hilt-plugin") {
        id = "hilt-plugin"
        implementationClass = "com.study.compose.gradleconfiguration.hilt.HiltPlugin"
    }

    plugins.register("app-base-plugin") {
        id = "app-base-plugin"
        implementationClass = "com.study.compose.gradleconfiguration.app.BaseAppPlugin"
    }

    plugins.register("android-lib-plugin") {
        id = "android-lib-plugin"
        implementationClass = "com.study.compose.gradleconfiguration.app.AndroidLibPlugin"
    }
}

dependencies {
    implementation(gradlePlugins.kotlin.gradle.plugin)
    implementation(gradlePlugins.gradle.build.tool)
}
