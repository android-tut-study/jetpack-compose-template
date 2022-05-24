package com.study.compose.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.study.compose.ui.common.components.ShrineScaffold
import com.study.compose.ui.common.components.ShrineTopBar
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.Cart
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
    onProductSelect: (cart: Cart) -> Unit
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
    onProductSelect: (cart: Cart) -> Unit
) {
    HomeScreen(
        viewState = HomeViewState(),
        onFilterPressed = onFilterPressed,
        onSearchPressed = onSearchPressed,
        onProductSelect = onProductSelect,
    )
}

@Composable
fun HomeScreen(
    viewState: HomeViewState,
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (cart: Cart) -> Unit
) {
    Products(
        onFilterPressed = onFilterPressed,
        onSearchPressed = onSearchPressed,
        onProductSelect = onProductSelect,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Products(
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (cart: Cart) -> Unit,
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
                modifier = Modifier.padding(vertical = 56.dp)
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


@Preview(widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    ShrineComposeTheme {
        Products(onProductSelect = {})
    }
}