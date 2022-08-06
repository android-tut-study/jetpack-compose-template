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
    implementation(domainModule("carts"))
    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(androidx.paging.runtime)
    implementation(androidx.paging.compose)
}