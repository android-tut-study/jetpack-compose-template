package com.study.compose.ui.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.SampleCartItems
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.ui.home.view.ProductsContent
import com.study.compose.ui.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {}
) {
    HomeScreen(
        viewModel = hiltViewModel(),
        onFilterPressed = onFilterPressed,
        onSearchPressed = onSearchPressed
    )
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {}
) {
    HomeScreen(
        viewState = HomeViewState(),
        onFilterPressed = onFilterPressed,
        onSearchPressed = onSearchPressed
    )
}

@Composable
fun HomeScreen(
    viewState: HomeViewState,
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {}
) {
    BoxWithConstraints {
        Products(
            onFilterPressed = onFilterPressed,
            onSearchPressed = onSearchPressed,
        )
        BottomCart(
            modifier = Modifier.align(Alignment.BottomEnd),
            maxHeight = maxHeight,
            maxWidth = maxWidth,
            carts = SampleCartItems
        )
    }
}

@Composable
fun Products(
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {}
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
        ProductsContent()
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
        Products()
    }
}