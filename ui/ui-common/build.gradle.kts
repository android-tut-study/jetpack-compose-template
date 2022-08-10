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
    api(androidxLibs.core.ktx)
    api(androidxLibs.appcompat)
    api(androidxLibs.compose.layout)
    api(androidxLibs.compose.material)
    // TODO add R8 config to optimize apk size by icon extension
    api(androidxLibs.compose.material.icons.extended)
    api(androidxLibs.compose.ui)
    api(androidxLibs.compose.ui.viewbinding)
    api(androidxLibs.compose.ui.util)
    api(androidxLibs.compose.ui.tooling.preview)
    debugApi(androidxLibs.compose.ui.tooling)

    debugApi(androidxLibs.customview.core)
    debugApi(androidxLibs.customview.poolingcontainer)

    testApi(testLibs.junit4)
    androidTestApi(testLibs.bundles.test.android.ui)
}
