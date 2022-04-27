package com.study.compose.shrine

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.study.compose.shrine.navigation.AppNavigation
import com.study.compose.shrine.navigation.Screen
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.BottomCart
import com.study.compose.ui.home.data.SampleCartItems
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
    val config = LocalConfiguration.current
    val currentRoute =
        navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)

    val state by appStateViewModel.state.collectAsState()
    if (currentRoute.value?.destination?.route != Screen.Landing.route) {
        BottomCart(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            maxHeight = config.screenHeightDp.dp,
            maxWidth = config.screenWidthDp.dp,
            carts = SampleCartItems,
            hidden = !state.showBottomCart
        )
    }
}
