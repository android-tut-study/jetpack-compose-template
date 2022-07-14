enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

// https://github.com/gradle/gradle/issues/20203
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {

        val hiltVersion = "2.42"
        create("gradlePlugins") {
            from(files("./gradle/catalog/gradlePlugins.versions.toml"))
            plugin("hilt", "com.google.dagger.hilt.android").version(hiltVersion)

            library(
                "hilt-app-plugin",
                "com.google.dagger",
                "dagger.hilt.android.plugin",
            ).withoutVersion()
        }

        create("androidx") {
            version("checkstyle", "8.37")

            library("appcompat", "androidx.appcompat:appcompat:1.4.1")

            version("activity", "1.6.0-alpha03")
            library(
                "activity-compose", "androidx.activity", "activity-compose"
            ).versionRef("activity")

            version("core", "1.7.0")
            library(
                "core-ktx", "androidx.core", "core-ktx"
            ).versionRef("core")

            version("compose", "1.3.0-alpha01")

            library(
                "compose-layout", "androidx.compose.foundation", "foundation-layout"
            ).versionRef("compose")

            library(
                "compose-material", "androidx.compose.material", "material"
            ).versionRef("compose")
            library(
                "compose-material-icons-extended",
                "androidx.compose.material",
                "material-icons-extended"
            ).versionRef("compose")

            library(
                "compose-ui", "androidx.compose.ui", "ui"
            ).versionRef("compose")
            library(
                "compose-ui-tooling", "androidx.compose.ui", "ui-tooling"
            ).versionRef("compose")
            library(
                "compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview"
            ).versionRef("compose")
            library(
                "compose-ui-viewbinding", "androidx.compose.ui", "ui-viewbinding"
            ).versionRef("compose")
            library(
                "compose-ui-util", "androidx.compose.ui", "ui-util"
            ).versionRef("compose")

            version("navigation", "2.4.2")
            library(
                "navigation-compose",
                "androidx.navigation",
                "navigation-compose"
            ).versionRef("navigation")

            version("lifecycle", "2.4.1")
            library(
                "lifecycle-livedata-ktx",
                "androidx.lifecycle",
                "lifecycle-livedata-ktx"
            ).versionRef("lifecycle")
            library(
                "lifecycle-viewmodel-compose",
                "androidx.lifecycle",
                "lifecycle-viewmodel-compose"
            ).versionRef("lifecycle")

            version("room", "2.5.0-alpha02")
            library("room-runtime", "androidx.room", "room-runtime").versionRef("lifecycle")
            library("room-compiler", "androidx.room", "room-compiler").versionRef("lifecycle")
            library("room-testing", "androidx.room", "room-testing").versionRef("lifecycle")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("lifecycle")
            library("room-paging", "androidx.room", "room-paging").versionRef("lifecycle")

            version("paging", "3.1.1")
            library("paging-runtime", "androidx.paging", "paging-runtime").versionRef("paging")
            library("paging-compose", "androidx.paging", "paging-compose").version("1.0.0-alpha15")

            version("camera", "1.2.0-alpha03")
            library("camera-core", "androidx.camera", "camera-core").versionRef("camera")
            library("camera-camera2", "androidx.camera", "camera-camera2").versionRef("camera")
            library("camera-lifecycle", "androidx.camera", "camera-lifecycle").versionRef("camera")
            library("camera-view", "androidx.camera", "camera-view").versionRef("camera")

            version("zxing", "3.4.1")
            library("zxing-core", "com.google.zxing", "core").versionRef("zxing")

            bundle(
                "camera-qr",
                listOf("camera-camera2", "camera-lifecycle", "camera-view", "zxing-core")
            )

            library("customview-core", "androidx.customview:customview:1.2.0-alpha01")
            library("customview-poolingcontainer", "androidx.customview:customview-poolingcontainer:1.0.0-rc01")
            bundle(
                "compose-customview",
                listOf("customview-core", "customview-poolingcontainer")
            )
        }

        create("hiltLibs") {
            version("hilt", hiltVersion)

            library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("hilt")
            library("hilt-android", "com.google.dagger", "hilt-android").versionRef("hilt")
            library(
                "hilt-android-compiler",
                "com.google.dagger",
                "hilt-android-compiler"
            ).versionRef("hilt")

            library(
                "hilt-navigation-compose",
                "androidx.hilt",
                "hilt-navigation-compose"
            ).version("1.0.0")

        }

        create("kotlinx") {
            version("coroutines", "1.6.2")
            library(
                "coroutines-core",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core"
            ).versionRef("coroutines")
        }

        create("square") {
            version("okhttp", "4.9.3")
            library("okhttp-core", "com.squareup.okhttp3", "okhttp").versionRef("okhttp")
            library(
                "okhttp-logging-interceptor",
                "com.squareup.okhttp3",
                "logging-interceptor"
            ).versionRef("okhttp")

            version("retrofit", "2.9.0")
            library("retrofit-core", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library(
                "retrofit-converter-moshi",
                "com.squareup.retrofit2",
                "converter-moshi"
            ).versionRef("retrofit")
        }

        create("io") {
            version("coil", "2.1.0")
            library("coil-core", "io.coil-kt", "coil").versionRef("coil")
            library("coil-compose", "io.coil-kt", "coil-compose").versionRef("coil")
        }

        create("test") {
            library("junit4", "junit:junit:4.13.2")
            library("espresso-core", "androidx.test.espresso:espresso-core:3.4.0")
            library("ui-junit4", "androidx.compose.ui:ui-test-junit4:1.1.1")

            bundle("test-android-ui", listOf("espresso-core", "ui-junit4"))
        }
    }
}

rootProject.name = "ShrineCompose"
includeBuild("includeBuild/gradleConfiguration")

include(":app")
include(":android-core:android-core-logger")
include(":android-core:android-core-camera")
include(":core:core-domain")
include(":core:core-usecase")
include(":core:core-dispatcher")
include(":core:core-result")
include(":domain:domain-products")
include(":domain:domain-carts")
include(":usecase:usecase-carts")
include(":usecase:usecase-products")
include(":ui:ui-state")
include(":ui:ui-common")
include(":ui:ui-landing")
include(":ui:ui-home")
include(":ui:ui-detail")
include(":ui:ui-cart")
include(":ui:ui-qr")