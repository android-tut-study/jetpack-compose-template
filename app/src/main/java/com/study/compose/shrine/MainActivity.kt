package com.study.compose.shrine

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.study.compose.shrine.navigation.AppNavigation
import com.study.compose.shrine.navigation.Screen
import com.study.compose.ui.cart.BottomCart
import com.study.compose.ui.common.components.AppBackground
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.state.AppState
import com.study.compose.ui.state.AppStateViewModel
import com.study.compose.ui.state.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            ShrineComposeTheme {
                AppBackground(modifier = Modifier.fillMaxSize()) {
                    AppContent(appState = appState, appStateViewModel = hiltViewModel())
                }
            }
        }
    }
}

@Composable
fun AppContent(
    appState: AppState,
    appStateViewModel: AppStateViewModel,
) {
    val navController = appState.navController
    BoxWithConstraints(Modifier.fillMaxSize()) {
        AppNavigation(
            navController = navController,
            appStateViewModel = appStateViewModel
        )
        BottomCart(
            modifier = Modifier.align(Alignment.BottomEnd),
            navController = navController,
            appStateViewModel = appStateViewModel
        )
    }
}

@Composable
fun BottomCart(
    modifier: Modifier = Modifier,
    navController: NavController,
    appStateViewModel: AppStateViewModel,
) {
    val currentRoute =
        navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)
    if (currentRoute.value?.destination?.route.orEmpty().contains(Screen.Products.route)) {
        BottomCart(
            modifier = modifier,
            appStateViewModel = appStateViewModel
        )
    }
}
