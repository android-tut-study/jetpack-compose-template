import com.study.compose.gradleconfiguration.util.uiModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    navigation { applied.set(true) }
}

uiConfiguration {
    applyCompose()
    applyUiTest()
}

dependencies {
    implementation(uiModule("common"))
}
