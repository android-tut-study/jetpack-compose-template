plugins {
    id("app-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
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
}
