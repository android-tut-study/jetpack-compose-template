import com.study.compose.gradleconfiguration.util.uiModule

plugins {
    id("app-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    navigation { applied.set(true) }
}

kapt {
    correctErrorTypes = true
}

uiConfiguration {
    applyCompose()
}

dependencies {
    implementation(uiModule("common"))
    implementation(uiModule("landing"))
    implementation(uiModule("home"))
    implementation(uiModule("detail"))
    implementation(uiModule("cart"))
    implementation(uiModule("qr"))

    // Core
    implementation(androidxLibs.core.ktx)
    implementation(androidxLibs.activity.compose)
    implementation(androidxLibs.navigation.compose)
    implementation(androidxLibs.appcompat)

    // LifeCycle
    implementation(androidxLibs.lifecycle.viewmodel.compose)
    implementation(androidxLibs.lifecycle.livedata.ktx)

    implementation(ioLibs.coil.core)
}
