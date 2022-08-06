import com.study.compose.gradleconfiguration.util.uiModule

plugins {
    id("android-lib-plugin")
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
    implementation(uiModule("common"))
}
