plugins {
    id("app-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    addNavigationDependency()
}

uiConfiguration {
    applyCompose()
    applyUiTest()
}

dependencies {
    implementation(project(":ui:ui-common"))
}
