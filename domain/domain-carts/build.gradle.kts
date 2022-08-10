import com.study.compose.gradleconfiguration.util.coreModule

plugins {
    id("android-lib-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(coreModule("domain"))
    implementation(coreModule("dispatcher"))

    implementation(kotlinxLibs.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(androidxLibs.room.runtime)
    implementation(androidxLibs.room.paging)
    implementation(androidxLibs.room.ktx)
    kapt(androidxLibs.room.compiler)

    implementation(androidxLibs.paging.runtime)

    testImplementation(androidxLibs.room.testing)
}
