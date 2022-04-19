import com.example.Libs

plugins {
    id("app-plugin")
}

dependencies {
    implementation(Libs.Kotlinx.Coroutines.CORE)
    implementation(project(":core:core-result"))
}