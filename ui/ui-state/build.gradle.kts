plugins {
    id("app-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    version.set(hiltLibs.versions.hilt)
    navigation {
        composeVersion.set(hiltLibs.versions.navigationCompose)
    }
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