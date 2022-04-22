import com.example.Libs

plugins {
    id("app-plugin")
    id("hilt-plugin")
    kotlin(com.example.GradlePlugins.Plugins.Kotlin.KAPT)
}

dependencies {
    api(project(":core:core-usecase"))
    implementation(project(":core:core-dispatcher"))
    api(project(":core:core-result"))
    implementation(project(":core:core-domain"))
    implementation(project(":domain:domain-products"))
    implementation(Libs.Kotlinx.Coroutines.CORE)
    implementation(Libs.Hilt.Core.ANDROID)
    kapt(Libs.Hilt.Core.ANDROID_COMPILER)
}