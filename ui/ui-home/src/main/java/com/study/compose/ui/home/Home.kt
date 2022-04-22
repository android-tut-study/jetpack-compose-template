package com.study.compose.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.Cart
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.ui.home.view.ProductsContent
import com.study.compose.ui.home.viewmodel.HomeViewModel

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
//    BoxWithConstraints(Modifier.fillMaxSize()) {

//        BottomCart(
//            modifier = Modifier.align(Alignment.BottomEnd),
//            maxHeight = maxHeight,
//            maxWidth = maxWidth,
//            carts = SampleCartItems
//        )
//    }
}

@Composable
fun Products(
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (cart: Cart) -> Unit
) {
    ShrineScaffold(
        topBar = {
            ShrineTopBar(
                navIcon = {
                    NavigationIcon(onNavIconPressed = {
                        // TODO open drawer
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
        drawerContent = { NavigationMenus() }
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