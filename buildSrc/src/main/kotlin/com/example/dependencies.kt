package com.example

object Libs {

    object AndroidX {
        object AppCompat {
            private const val VERSION = "1.4.1"
            const val APPCOMPAT = "androidx.appcompat:appcompat:$VERSION"
        }

        object Activity {
            private const val VERSION = "1.4.0"
            const val ACTIVITY_KTX = "androidx.activity:activity-ktx:$VERSION"
            const val ACTIVITY = "androidx.activity:activity:$VERSION"
            const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$VERSION"
        }

        object Core {
            private const val VERSION = "1.7.0"
            const val KTX = "androidx.core:core-ktx:$VERSION"
        }

        object Compose {
            private const val VERSION = SharedVersion.AndroidX.COMPOSE

            const val FOUNDATION = "androidx.compose.foundation:foundation:$VERSION"
            const val LAYOUT = "androidx.compose.foundation:foundation-layout:$VERSION"
            const val MATERIAL = "androidx.compose.material:material:$VERSION"
            const val MATERIAL_ICONS_EXTENDED =
                "androidx.compose.material:material-icons-extended:$VERSION"
            const val RUNTIME = "androidx.compose.runtime:runtime:$VERSION"
            const val RUNTIME_LIVEDATA = "androidx.compose.runtime:runtime-livedata:$VERSION"
            const val TOOLING = "androidx.compose.ui:ui-tooling:$VERSION"
            const val TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$VERSION"
            const val TEST = "androidx.compose.ui:ui-test:$VERSION"
            const val UI = "androidx.compose.ui:ui:$VERSION"
            const val UI_TEST = "androidx.compose.ui:ui-test-junit4:$VERSION"
            const val UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:$VERSION"
            const val UI_UTIL = "androidx.compose.ui:ui-util:$VERSION"
            const val VIEW_BINDING = "androidx.compose.ui:ui-viewbinding:$VERSION"
            const val COMPILER = "androidx.compose.compiler:compiler:$VERSION"

            object Material3 {
                private const val VERSION = "1.0.0-alpha09"
                const val MATERIAL3 = "androidx.compose.material3:material3:$VERSION"
            }
        }

        object Navigation {
            private const val VERSION = "2.3.5"

            // JAVA
            const val FRAGMENT = "androidx.navigation:navigation-fragment:$VERSION"
            const val UI = "androidx.navigation:navigation-ui:$VERSION"

            // Kotlin
            const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:$VERSION"
            const val UI_KTX = "androidx.navigation:navigation-ui-ktx:$VERSION"

            const val RUNTIME = "androidx.navigation:navigation-runtime:$VERSION"
            const val RUNTIME_KTX = "androidx.navigation:navigation-runtime-ktx:$VERSION"
            const val DYNAMIC_FEATURES_FRAGMENT =
                "androidx.navigation:navigation-dynamic-features-fragment:$VERSION"
        }

        object Fragment {
            private const val VERSION = "1.3.0-alpha08"
            const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:$VERSION"
            const val FRAGMENT = "androidx.fragment:fragment:$VERSION"
        }


        object LifeCycle {
            private const val VERSION = "2.4.1"
            const val EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$VERSION"
            const val COMMON = "androidx.lifecycle:lifecycle-common:$VERSION"
            const val RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
            const val RUNTIME = "androidx.lifecycle:lifecycle-runtime:$VERSION"
            const val COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:$VERSION"
            const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel:$VERSION"
            const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
            const val LIVEDATA_CORE = "androidx.lifecycle:lifecycle-livedata-core:$VERSION"
            const val LIVEDATA_CORE_KTX = "androidx.lifecycle:lifecycle-livedata-core-ktx:$VERSION"
            const val LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:$VERSION"
            const val VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:$VERSION"
        }
    }

}