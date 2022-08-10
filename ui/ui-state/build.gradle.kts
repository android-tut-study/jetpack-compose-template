plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    navigation { applied.set(true) }
}

uiConfiguration {
    applyCompose()
    applyUiTest()
}

dependencies {
    implementation(androidxLibs.navigation.compose)
    implementation(androidxLibs.compose.layout)
    implementation(androidxLibs.compose.material)
    implementation(androidxLibs.compose.ui)

    testApi(testLibs.junit4)
    androidTestApi(testLibs.bundles.test.android.ui)
}