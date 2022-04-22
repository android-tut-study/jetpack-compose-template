plugins {
    id("app-plugin")
    id("hilt-plugin")
}

uiConfiguration {
    applyCompose()
}

hiltConfiguration {
    addNavigationDependency()
}

dependencies {
    implementation(project(":ui:ui-common"))
}