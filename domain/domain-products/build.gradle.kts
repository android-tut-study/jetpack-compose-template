plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-dispatcher"))

    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    // Retrofit
    implementation(square.okhttp.core)
    implementation(square.okhttp.logging.interceptor)
    implementation(square.retrofit.core)
    implementation(square.retrofit.converter.moshi)

}