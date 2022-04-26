package com.study.compose.ui.home

import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.Cart
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.ui.home.view.ProductsContent
import com.study.compose.ui.home.viewmodel.HomeViewModel
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
    onProductSelect: (cart: Cart) -> Unit
) {
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    var backdropRevealed by remember { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()

    ShrineScaffold(
        topBar = {
            ShrineTopBar(
                backdropRevealed = backdropRevealed,
                navIcon = {
                    NavigationIcon(
                        backdropRevealed = backdropRevealed,
                        onRevealed = { revealed ->
                            if (!scaffoldState.isAnimationRunning) {
                                backdropRevealed = revealed
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
                }
            )
        },
        drawerContent = { NavigationMenus() },
        scaffoldState = scaffoldState
    ) {
        ProductsContent(onProductSelect = onProductSelect)
    }
}


@Composable
fun NavigationMenus() {
    ShrineDrawer()
}


@Preview
@Composable
fun HomeScreenPreview() {
    ShrineComposeTheme {
        Products(onProductSelect = {})
    }
}