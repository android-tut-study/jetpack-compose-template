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
    implementation(androidx.navigation.compose)
    implementation(androidx.compose.layout)
    implementation(androidx.compose.material)
    implementation(androidx.compose.ui)

    testApi(test.junit4)
    androidTestApi(test.bundles.test.android.ui)
}