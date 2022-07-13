plugins {
    id("app-plugin")
    id("hilt-plugin")
    kotlin(com.example.GradlePlugins.Plugins.Kotlin.KAPT)
}

dependencies {
    api(project(":core:core-usecase"))
    implementation(project(":core:core-dispatcher"))
    api(project(":core:core-result"))
    api(project(":core:core-domain"))
    implementation(project(":domain:domain-products"))
    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)
}