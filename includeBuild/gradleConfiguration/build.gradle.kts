@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `kotlin-dsl`.version("2.1.7")
    checkstyle
    alias(gradleConfig.plugins.ktlint)
}

checkstyle {
    // will use the version declared in the catalog
    toolVersion = gradleConfig.versions.checkstyle.get()
}

allprojects {
    apply {
        plugin(rootProject.gradleConfig.plugins.ktlint.get().pluginId)
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
        implementationClass = "com.study.compose.gradleconfiguration.AppPlugin"
    }

    plugins.register("hilt-plugin") {
        id = "hilt-plugin"
        implementationClass = "com.study.compose.gradleconfiguration.HiltPlugin"
    }
}

dependencies {
    implementation(gradleConfig.kotlin.gradle.plugin)
    implementation(gradleConfig.gradle.build.tool)
}
