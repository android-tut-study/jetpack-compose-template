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
    implementation(project(":ui:ui-common"))
    implementation(project(":android-core:android-core-camera"))
    implementation(io.coil.compose)
}
