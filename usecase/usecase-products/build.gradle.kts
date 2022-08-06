import com.study.compose.gradleconfiguration.util.coreModule
import com.study.compose.gradleconfiguration.util.domainModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

dependencies {
    api(coreModule("usecase"))
    implementation(coreModule("dispatcher"))
    api(coreModule("result"))
    api(coreModule("domain"))
    implementation(domainModule("products"))
    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)
}