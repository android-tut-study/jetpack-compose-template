import com.example.Libs

plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(Libs.Kotlinx.Coroutines.CORE)
    implementation(Libs.Hilt.Core.ANDROID)
    kapt(Libs.Hilt.Core.ANDROID_COMPILER)
}