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
    implementation(Libs.IO.Coil.COIL_COMPOSE)
    implementation(project(":ui:ui-common"))
    implementation(project(":usecase:usecase-carts"))

    implementation(Libs.AndroidX.Paging.RUNTIME)
    implementation(Libs.AndroidX.Paging.COMPOSE)
}