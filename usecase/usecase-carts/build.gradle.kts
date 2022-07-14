plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    api(project(":core:core-usecase"))
    implementation(project(":core:core-dispatcher"))
    api(project(":core:core-result"))
    api(project(":core:core-domain"))
    implementation(project(":domain:domain-carts"))
    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(androidx.paging.runtime)
    implementation(androidx.paging.compose)
}