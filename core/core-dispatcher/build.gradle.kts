plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

libraryConfiguration {
    applyTestFixture(true)
}

dependencies {
    implementation(kotlinxLibs.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    testFixturesApi(kotlinxLibs.coroutines.test)
    testFixturesApi(testLibs.junit4)
}
