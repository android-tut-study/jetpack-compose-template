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
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.android.tools.build:gradle:7.1.2")
}