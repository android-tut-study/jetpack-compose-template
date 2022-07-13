plugins {
    id("app-plugin")
    id("hilt-plugin")
}

dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-dispatcher"))

    implementation(kotlinx.coroutines.core)
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(androidx.room.runtime)
    implementation(androidx.room.paging)
    implementation(androidx.room.ktx)
    kapt(androidx.room.compiler)

//    implementation(Libs.AndroidX.Paging.RUNTIME)
    implementation(androidx.paging.runtime)

    testImplementation(androidx.room.testing)

}