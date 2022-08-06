import com.study.compose.gradleconfiguration.util.androidCoreModule
import com.study.compose.gradleconfiguration.util.uiModule

plugins {
    id("android-lib-plugin")
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
    implementation(uiModule("common"))
    implementation(androidCoreModule("camera"))
    implementation(io.coil.compose)
}
