import com.example.Libs

plugins {
    id("app-plugin")
}

uiConfiguration {
    applyCompose()
}

dependencies {
    api(project(":ui:ui-state"))
    api(project(":android-core:android-core-logger"))
    api(Libs.AndroidX.Core.KTX)
    api(Libs.AndroidX.AppCompat.APPCOMPAT)
    api(Libs.AndroidX.Compose.LAYOUT)
    api(Libs.AndroidX.Compose.UI)
    api(Libs.AndroidX.Compose.MATERIAL)
    api(Libs.AndroidX.Compose.VIEW_BINDING)
    api(Libs.AndroidX.Compose.TOOLING_PREVIEW)
    debugApi(Libs.AndroidX.Compose.TOOLING)
    // TODO add R8 config to optimize apk size by icon extension
    api(Libs.AndroidX.Compose.MATERIAL_ICONS_EXTENDED)
    api(Libs.AndroidX.Compose.UI_UTIL)

    // TODO Remove them after jetpack compose stable
    debugApi("androidx.customview:customview:1.2.0-alpha01")
    debugApi("androidx.customview:customview-poolingcontainer:1.0.0-beta02")

    testApi("junit:junit:4.13.2")
    androidTestApi("androidx.test.ext:junit:1.1.3")
    androidTestApi("androidx.test.espresso:espresso-core:3.4.0")

}