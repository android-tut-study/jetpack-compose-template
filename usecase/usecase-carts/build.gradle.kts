import com.example.Libs

plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    api(project(":core:core-usecase"))
    implementation(project(":core:core-dispatcher"))
    api(project(":core:core-result"))
    api(project(":core:core-domain"))
    implementation(project(":domain:domain-carts"))
    implementation(Libs.Kotlinx.Coroutines.CORE)
    implementation(Libs.Hilt.Core.ANDROID)
    kapt(Libs.Hilt.Core.ANDROID_COMPILER)
}