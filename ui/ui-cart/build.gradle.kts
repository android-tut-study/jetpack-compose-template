import com.study.compose.gradleconfiguration.util.uiModule
import com.study.compose.gradleconfiguration.util.useCaseModule

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
    implementation(io.coil.compose)
    implementation(uiModule("common"))
    implementation(useCaseModule("carts"))

    implementation(androidx.paging.runtime)
    implementation(androidx.paging.compose)
}
