import com.study.compose.gradleconfiguration.util.coreModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(coreModule("domain"))
    implementation(coreModule("dispatcher"))

    implementation(kotlinxLibs.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    // Retrofit
    implementation(squareLibs.okhttp.core)
    implementation(squareLibs.okhttp.logging.interceptor)
    implementation(squareLibs.retrofit.core)
    implementation(squareLibs.retrofit.converter.moshi)

    testImplementation(kotlinxLibs.coroutines.test)
    testImplementation(ioLibs.mockk.core)

//    testImplementation(testFixtures(project(":core:core-dispatcher")))
}
