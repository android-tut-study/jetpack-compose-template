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
    implementation(project(":usecase:usecase-products"))
    implementation(project(":usecase:usecase-carts"))
    implementation(io.coil.compose)
}
