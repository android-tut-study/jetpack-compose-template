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
    navigation { applied.set(true) }
}

dependencies {
    implementation(uiModule("common"))
    implementation(useCaseModule("products"))
    implementation(useCaseModule("carts"))
    implementation(androidCoreModule("qr"))
    implementation(ioLibs.coil.compose)
}
