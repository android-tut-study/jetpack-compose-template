import com.example.Libs

plugins {
    id("app-plugin")
    id("hilt-plugin")
    kotlin(com.example.GradlePlugins.Plugins.Kotlin.KAPT)
}

dependencies {
    implementation(Libs.Kotlinx.Coroutines.CORE)
    implementation(Libs.Hilt.Core.ANDROID)
    kapt(Libs.Hilt.Core.ANDROID_COMPILER)
    implementation(project(":core:core-domain"))

    // Retrofit
    implementation(Libs.Square.OkHttp.OKHTTP)
    implementation(Libs.Square.OkHttp.LOGGING_INTERCEPTOR)
    implementation(Libs.Square.Retrofit.RETROFIT)
    implementation(Libs.Square.Retrofit.Converters.MOSHI)

}