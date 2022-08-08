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
    implementation(androidx.core.ktx)
    implementation(androidx.activity.compose)
    implementation(androidx.navigation.compose)
    implementation(androidx.appcompat)

    // LifeCycle
    implementation(androidx.lifecycle.viewmodel.compose)
    implementation(androidx.lifecycle.livedata.ktx)

    implementation(io.coil.core)
}
