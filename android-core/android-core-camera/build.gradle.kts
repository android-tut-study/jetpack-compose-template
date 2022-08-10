plugins {
    id("android-lib-plugin")
}

dependencies {
    api(androidxLibs.bundles.camera)
    implementation(androidxLibs.zxing.core)
}
