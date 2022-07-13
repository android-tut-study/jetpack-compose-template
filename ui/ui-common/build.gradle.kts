plugins {
    id("app-plugin")
}

uiConfiguration {
    applyCompose()
}

dependencies {
    api(project(":ui:ui-state"))
    api(project(":android-core:android-core-logger"))
    api(androidx.core.ktx)
    api(androidx.appcompat)
    api(androidx.compose.layout)
    api(androidx.compose.material)
    // TODO add R8 config to optimize apk size by icon extension
    api(androidx.compose.material.icons.extended)
    api(androidx.compose.ui)
    api(androidx.compose.ui.viewbinding)
    api(androidx.compose.ui.util)
    debugApi(androidx.compose.ui.tooling)
    debugApi(androidx.compose.ui.tooling.preview)

    // TODO Remove them after jetpack compose stable
    debugApi("androidx.customview:customview:1.2.0-alpha01")
    debugApi("androidx.customview:customview-poolingcontainer:1.0.0-beta02")

    testApi(test.junit4)
    androidTestApi(test.bundles.test.android.ui)

}