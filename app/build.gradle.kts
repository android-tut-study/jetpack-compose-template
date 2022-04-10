import com.example.Libs

plugins {
    id(com.example.GradlePlugins.Plugins.Android.APPLICATION)
    id(com.example.GradlePlugins.Plugins.Jetbrains.Kotlin.ANDROID)
    kotlin(com.example.GradlePlugins.Plugins.Kotlin.KAPT)
    id(com.example.GradlePlugins.Plugins.Hilt.PLUGIN)
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        composeOptions {
            kotlinCompilerExtensionVersion = com.example.SharedVersion.AndroidX.COMPOSE
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

dependencies {
    implementation(project(":ui:ui-common"))
    implementation(project(":ui:ui-landing"))
    implementation(project(":ui:ui-home"))

    // Core
    implementation(Libs.AndroidX.Core.KTX)
    implementation(Libs.AndroidX.Activity.ACTIVITY_COMPOSE)
    implementation(Libs.AndroidX.AppCompat.APPCOMPAT)

    // LifeCycle
    implementation(Libs.AndroidX.LifeCycle.VIEWMODEL_COMPOSE)
    implementation(Libs.AndroidX.LifeCycle.LIVEDATA_KTX)
//    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'

    // Navigation
    implementation(Libs.AndroidX.Navigation.FRAGMENT_KTX)
    implementation(Libs.AndroidX.Navigation.UI_KTX)

    // Hilt
    implementation(Libs.Hilt.Core.ANDROID)
    kapt(Libs.Hilt.Core.ANDROID_COMPILER)

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
}