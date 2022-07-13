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
    implementation(project(":ui:ui-common"))
    implementation(project(":android-core:android-core-camera"))
    implementation(io.coil.compose)
}