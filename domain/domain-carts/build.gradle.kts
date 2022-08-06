import com.study.compose.gradleconfiguration.util.coreModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(coreModule("domain"))
    implementation(coreModule("dispatcher"))

    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(androidx.room.runtime)
    implementation(androidx.room.paging)
    implementation(androidx.room.ktx)
    kapt(androidx.room.compiler)

    implementation(androidx.paging.runtime)

    testImplementation(androidx.room.testing)
}
