import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
includeBuild("includeBuild/gradleConfiguration")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

// https://github.com/gradle/gradle/issues/20203
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
    val catalogPath = "./gradle/catalog"

    versionCatalogs {

        create("gradlePlugins") {
            from(files("$catalogPath/gradlePlugins.versions.toml"))
        }

        create("hiltLibs") {
            from(files("$catalogPath/hilt.versions.toml"))
        }

        create("androidxLibs") {
            from(files("$catalogPath/androidx.versions.toml"))
        }

        create("kotlinx") {
            version("coroutines", "1.6.2")
            library(
                "coroutines-core",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core"
            ).versionRef("coroutines")

            library(
                "coroutines-test",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-test"
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

            version("mockk", "1.12.4")
            library("mockk-core", "io.mockk", "mockk").versionRef("mockk")
            library("mockk-android", "io.mockk", "mockk-android").versionRef("mockk")
            library("mockk-agent-jvm", "io.mockk", "mockk-agent-jvm").versionRef("mockk")
        }

        create("test") {
            library("junit4", "junit:junit:4.13.2")
            library("espresso-core", "androidx.test.espresso:espresso-core:3.4.0")
            library("ui-junit4", "androidx.compose.ui:ui-test-junit4:1.1.1")
            library("junit-ext", "androidx.test.ext:junit:1.1.3")

            bundle("test-android-ui", listOf("espresso-core", "ui-junit4"))
        }
    }
}

rootProject.name = "ShrineCompose"

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
include(":android-core:android-core-qr")
