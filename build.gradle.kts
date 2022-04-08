plugins {
    id(com.example.GradlePlugins.Plugins.Android.APPLICATION) version com.example.SharedVersion.AndroidPlugins.CORE apply false
    id(com.example.GradlePlugins.Plugins.Android.LIBRARY) version com.example.SharedVersion.AndroidPlugins.CORE apply false
    id(com.example.GradlePlugins.Plugins.Jetbrains.Kotlin.ANDROID) version com.example.SharedVersion.KotlinPlugins.ANDROID apply false
    id(com.example.GradlePlugins.Plugins.Hilt.GRADLE) version com.example.SharedVersion.Hilt.CORE apply false
}

tasks.register("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
