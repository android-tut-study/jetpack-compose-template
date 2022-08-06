import com.study.compose.gradleconfiguration.util.androidCoreModule
import com.study.compose.gradleconfiguration.util.uiModule
import com.study.compose.gradleconfiguration.util.useCaseModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

uiConfiguration {
    applyCompose()
    applyUiTest()
}

hiltConfiguration {
    version.set(hiltLibs.versions.hilt)
    navigation {
        composeVersion.set(hiltLibs.versions.navigationCompose)
    }
}

dependencies {
    implementation(uiModule("common"))
    implementation(useCaseModule("products"))
    implementation(useCaseModule("carts"))
    implementation(androidCoreModule("qr"))
    implementation(io.coil.compose)
}
