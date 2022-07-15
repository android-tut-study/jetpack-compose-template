plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)
}
