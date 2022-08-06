plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

libraryConfiguration {
    applyTestFixture(true)
}

dependencies {
    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    testFixturesApi(kotlinx.coroutines.test)
    testFixturesApi(test.junit4)
}
