import com.study.compose.gradleconfiguration.util.uiModule

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(gradlePlugins.plugins.application)
    alias(gradlePlugins.plugins.kotlin)
    kotlin("kapt")
    id(gradlePlugins.hilt.app.plugin.get().module.name)
    id("app-plugin")
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

    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.android.compiler)
    implementation(hiltLibs.hilt.navigation.compose)

    // LifeCycle
    implementation(androidx.lifecycle.viewmodel.compose)
    implementation(androidx.lifecycle.livedata.ktx)

    // Hilt
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(io.coil.core)
}
