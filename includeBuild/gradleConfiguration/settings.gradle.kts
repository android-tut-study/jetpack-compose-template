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
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        gradlePluginPortal()
    }

    versionCatalogs {

        create("gradleConfig") {
            from(files("../../gradle/catalog/gradlePlugins.versions.toml"))
        }
    }
}