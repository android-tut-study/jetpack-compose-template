import com.study.compose.gradleconfiguration.util.coreModule

plugins {
    id("android-lib-plugin")
}

dependencies {
    implementation(kotlinxLibs.coroutines.core)
    implementation(coreModule("result"))
}
