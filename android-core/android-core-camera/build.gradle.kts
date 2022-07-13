plugins {
    id("app-plugin")
}

dependencies {
    // CameraX
//    api(Libs.AndroidX.Camera.CAMERA2)
//    api(Libs.AndroidX.Camera.LIFECYCLE)
//    api(Libs.AndroidX.Camera.VIEW)
//    api(Libs.ZXING.CORE)
    api(androidx.bundles.camera.qr)
}