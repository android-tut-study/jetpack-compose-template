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
    implementation(io.coil.compose)
    implementation(project(":ui:ui-common"))
}
