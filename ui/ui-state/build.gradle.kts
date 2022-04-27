import com.example.Libs

plugins {
    id("app-plugin")
    id("hilt-plugin")
}

hiltConfiguration {
    addNavigationDependency()
}

uiConfiguration {
    applyCompose()
}

dependencies {
    implementation(Libs.AndroidX.Navigation.COMPOSE)
    implementation(Libs.AndroidX.Compose.LAYOUT)
    implementation(Libs.AndroidX.Compose.UI)
    implementation(Libs.AndroidX.Compose.MATERIAL)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}