plugins {
    id("app-plugin")
}

dependencies {
    implementation(kotlinx.coroutines.core)
    implementation(project(":core:core-result"))
}