plugins {
    id("app-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    addNavigationDependency()
}

uiConfiguration {
    applyCompose()
}

dependencies {
    implementation(androidx.navigation.compose)
    implementation(androidx.compose.layout)
    implementation(androidx.compose.material)
    implementation(androidx.compose.ui)

    testApi(test.junit4)
    androidTestApi(test.bundles.test.android.ui)
}