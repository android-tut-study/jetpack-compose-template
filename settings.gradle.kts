enableFeaturePreview("VERSION_CATALOGS")

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

        create("squareLibs") {
            from(files("$catalogPath/square.versions.toml"))
        }

        create("ioLibs") {
            from(files("$catalogPath/io.versions.toml"))
        }

        create("kotlinxLibs") {
            from(files("$catalogPath/kotlinx.versions.toml"))
        }

        create("testLibs") {
            from(files("$catalogPath/test.versions.toml"))
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
