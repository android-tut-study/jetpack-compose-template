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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.study.compose.shrine.navigation.AppNavigation
import com.study.compose.shrine.navigation.Screen
import com.study.compose.ui.cart.BottomCart
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.state.AppStateViewModel
import com.study.compose.ui.state.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppContent() {
    val appState = rememberAppState()
    val navController = appState.navController
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val appStateViewModel = viewModel<AppStateViewModel>(viewModelStoreOwner = viewModelStoreOwner)

    ShrineComposeTheme {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            AppNavigation(
                navController = navController,
                viewModelStoreOwner = viewModelStoreOwner
            )
            BottomCart(
                modifier = Modifier.align(Alignment.BottomEnd),
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
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
