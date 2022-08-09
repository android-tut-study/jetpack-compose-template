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

        val catalogPath = "../../gradle/catalog"
        create("gradleConfig") {
            from(files("${catalogPath}/gradlePlugins.versions.toml"))
        }

        create("hiltLibs") {
            from(files("${catalogPath}/hilt.versions.toml"))
        }

        create("androidxLibs") {
            from(files("${catalogPath}/androidx.versions.toml"))
        }
    }
}
