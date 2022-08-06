import com.study.compose.gradleconfiguration.util.androidCoreModule
import com.study.compose.gradleconfiguration.util.uiModule

plugins {
    id("android-lib-plugin")
}

uiConfiguration {
    applyCompose()
    applyUiTest()
}

dependencies {
    api(uiModule("state"))
    api(androidCoreModule("logger"))
    api(androidx.core.ktx)
    api(androidx.appcompat)
    api(androidx.compose.layout)
    api(androidx.compose.material)
    // TODO add R8 config to optimize apk size by icon extension
    api(androidx.compose.material.icons.extended)
    api(androidx.compose.ui)
    api(androidx.compose.ui.viewbinding)
    api(androidx.compose.ui.util)
    api(androidx.compose.ui.tooling.preview)
    debugApi(androidx.compose.ui.tooling)

    debugApi(androidx.customview.core)
    debugApi(androidx.customview.poolingcontainer)

    testApi(test.junit4)
    androidTestApi(test.bundles.test.android.ui)
}
