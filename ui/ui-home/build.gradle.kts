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
    implementation(io.coil.compose)
    implementation(project(":ui:ui-common"))
    implementation(project(":usecase:usecase-products"))
    implementation(project(":usecase:usecase-carts"))
}
