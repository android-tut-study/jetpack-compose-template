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

    implementation(Libs.AndroidX.Room.KTX)
    implementation(Libs.AndroidX.Room.RUNTIME)
    kapt(Libs.AndroidX.Room.COMPILER)

    testImplementation(Libs.AndroidX.Room.TEST_HELPERS)

}