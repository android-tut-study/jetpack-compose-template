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
    implementation(io.coil.compose)
    implementation(project(":ui:ui-common"))
    implementation(project(":usecase:usecase-carts"))

    implementation(androidx.paging.runtime)
    implementation(androidx.paging.compose)
}
