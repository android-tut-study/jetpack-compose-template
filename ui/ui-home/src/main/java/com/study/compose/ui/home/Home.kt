package com.study.compose.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.study.compose.ui.common.components.ShrineScaffold
import com.study.compose.ui.common.components.ShrineTopBar
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.Product
import com.study.compose.ui.home.interactor.intent.HomeIntent
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.ui.home.view.ProductsContent
import com.study.compose.ui.home.viewmodel.HomeViewModel
import com.study.compose.ui.state.AppStateViewModel
import com.study.compose.ui.state.rememberAppState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (product: Product) -> Unit
) {
    HomeScreen(
        viewModel = hiltViewModel(),
        onFilterPressed = onFilterPressed,
        onSearchPressed = onSearchPressed,
        onProductSelect = onProductSelect,
    )
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (product: Product) -> Unit
) {
    val homeViewState = viewModel.viewState.collectAsState()
    // TODO Test
    LaunchedEffect(key1 = homeViewState) {
        viewModel.processIntent(HomeIntent.FetchProducts)
    }
    Products(
        viewState = homeViewState.value,
        onFilterPressed = onFilterPressed,
        onSearchPressed = onSearchPressed,
        onProductSelect = onProductSelect,
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Products(
    viewState: HomeViewState,
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (product: Product) -> Unit,
) {
    val appState = rememberAppState()
    val appViewStateVM: AppStateViewModel = viewModel()
    val scaffoldState = appState.scaffoldState
    var backdropRevealed by remember { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        ShrineScaffold(
            topBar = {
                ShrineTopBar(
                    navIcon = {
                        NavigationIcon(
                            backdropRevealed = backdropRevealed,
                            onRevealed = { revealed ->
                                if (!scaffoldState.isAnimationRunning) {
                                    backdropRevealed = revealed
                                    appViewStateVM.shouldShowBottomCart(!revealed)
                                    scope.launch {
                                        if (scaffoldState.isConcealed) {
                                            scaffoldState.reveal()
                                        } else {
                                            scaffoldState.conceal()
                                        }
                                    }
                                }
                            })
                    },
                    actions = {
                        HomeActionIcon(
                            com.study.compose.ui.common.R.drawable.shr_search,
                            onPressed = onSearchPressed
                        )
                        HomeActionIcon(
                            com.study.compose.ui.common.R.drawable.shr_filter,
                            onPressed = onFilterPressed
                        )
                    },
                    title = { TopHeader(backdropRevealed = backdropRevealed) }
                )
            },
            backLayerContent = { NavigationMenus(backdropRevealed = backdropRevealed) },
            scaffoldState = scaffoldState
        ) {
            ProductsContent(
                onProductSelect = onProductSelect,
                modifier = Modifier.padding(vertical = 56.dp),
                products = viewState.product.products
            )
        }
    }

}


@Composable
fun NavigationMenus(backdropRevealed: Boolean) {
    ShrineDrawer(
        backdropRevealed = backdropRevealed,
        // This padding so useful. It prevent crash in BackdropScaffold caused by peekheight
        modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
    )
}


//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun HomeScreenPreview() {
//    ShrineComposeTheme {s
//        Products(onProductSelect = {})
//    }
//}