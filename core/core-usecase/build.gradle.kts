import com.study.compose.gradleconfiguration.util.coreModule

plugins {
    id("android-lib-plugin")
}

dependencies {
    implementation(kotlinx.coroutines.core)
    implementation(coreModule("result"))
}
