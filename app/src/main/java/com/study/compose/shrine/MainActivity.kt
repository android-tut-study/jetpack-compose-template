package com.study.compose.shrine

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.study.compose.shrine.navigation.AppNavigation
import com.study.compose.shrine.navigation.Screen
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.BottomCart
import com.study.compose.ui.home.data.SampleCartItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }

    @Composable
    fun AppContent() {
        val navController = rememberNavController()
        ShrineComposeTheme {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val currentRoute =
                    navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)
                AppNavigation(
                    navController = navController
                )

                if (currentRoute.value?.destination?.route != Screen.Landing.route) {
                    BottomCart(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        maxHeight = maxHeight,
                        maxWidth = maxWidth,
                        carts = SampleCartItems
                    )
                }
            }
        }
    }
}