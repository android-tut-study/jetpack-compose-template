plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}


repositories {
    google()
    mavenCentral()
    jcenter() // Warning: this repository is going to shut down soon
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("ui-plugins") {
        id = "ui-plugins"
        implementationClass = "com.study.compose.gradleconfiguration.UIPlugins"
    }

    plugins.register("hilt-plugins") {
        id = "hilt-plugins"
        implementationClass = "com.study.compose.gradleconfiguration.HiltPlugins"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.android.tools.build:gradle:7.1.2")
}