import com.study.compose.gradleconfiguration.util.coreModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(coreModule("domain"))
    implementation(coreModule("dispatcher"))

    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    // Retrofit
    implementation(square.okhttp.core)
    implementation(square.okhttp.logging.interceptor)
    implementation(square.retrofit.core)
    implementation(square.retrofit.converter.moshi)

    testImplementation(kotlinx.coroutines.test)
    testImplementation(io.mockk.core)

//    testImplementation(testFixtures(project(":core:core-dispatcher")))
}
