@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(gradlePlugins.plugins.application)
    alias(gradlePlugins.plugins.kotlin)
    kotlin("kapt")
    id(gradlePlugins.hilt.app.plugin.get().module.name)
}

project.extensions.configure(com.android.build.gradle.BaseExtension::class.java) {
    compileSdkVersion(32)
    defaultConfig {
        applicationId = "com.study.compose.shrine"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        composeOptions {
            kotlinCompilerExtensionVersion = "1.2.0"
        }
    }

    buildFeatures.compose = true
    buildFeatures.viewBinding = true
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":ui:ui-common"))
    implementation(project(":ui:ui-landing"))
    implementation(project(":ui:ui-home"))
    implementation(project(":ui:ui-detail"))
    implementation(project(":ui:ui-cart"))
    implementation(project(":ui:ui-qr"))

    // Core
    implementation(androidx.core.ktx)
    implementation(androidx.activity.compose)
    implementation(androidx.navigation.compose)
    implementation(androidx.appcompat)

    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.android.compiler)
    implementation(hiltLibs.hilt.navigation.compose)

    // LifeCycle
    implementation(androidx.lifecycle.viewmodel.compose)
    implementation(androidx.lifecycle.livedata.ktx)

    // Hilt
    implementation(hiltLibs.hilt.android)
    kapt(hiltLibs.hilt.compiler)

    implementation(io.coil.core)
}
