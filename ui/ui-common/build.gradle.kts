import com.example.Libs

plugins {
    id("ui-plugins")
}

dependencies {
    api(Libs.AndroidX.Core.KTX)
    api(Libs.AndroidX.AppCompat.APPCOMPAT)
    api(Libs.AndroidX.Compose.LAYOUT)
    api(Libs.AndroidX.Compose.UI)
    api(Libs.AndroidX.Compose.MATERIAL)
    api(Libs.AndroidX.Compose.VIEW_BINDING)
    api(Libs.AndroidX.Compose.TOOLING_PREVIEW)
    api(Libs.AndroidX.Compose.MATERIAL_ICONS_EXTENDED)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

}