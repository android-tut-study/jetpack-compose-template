import com.example.Libs

plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-dispatcher"))

    implementation(Libs.Kotlinx.Coroutines.CORE)
    implementation(Libs.Hilt.Core.ANDROID)
    kapt(Libs.Hilt.Core.ANDROID_COMPILER)

    // Retrofit
    implementation(Libs.Square.OkHttp.OKHTTP)
    implementation(Libs.Square.OkHttp.LOGGING_INTERCEPTOR)
    implementation(Libs.Square.Retrofit.RETROFIT)
    implementation(Libs.Square.Retrofit.Converters.MOSHI)

}