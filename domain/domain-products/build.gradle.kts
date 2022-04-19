plugins {
    id("app-plugin")
    id("hilt-plugin")
    kotlin(com.example.GradlePlugins.Plugins.Kotlin.KAPT)
}

dependencies {
    implementation(com.example.Libs.Kotlinx.Coroutines.CORE)
    implementation(com.example.Libs.Hilt.Core.ANDROID)
    kapt(com.example.Libs.Hilt.Core.ANDROID_COMPILER)
    implementation(project(":core:core-domain"))
}