plugins {
    id("ui-plugins")
    id("hilt-plugins")
}

dependencies {
    implementation(project(":ui:ui-common"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

configuration {
    addNavigationDependency()
}