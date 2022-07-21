package com.study.compose.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.study.compose.ui.common.components.ShrineScaffold
import com.study.compose.ui.common.components.ShrineTopBar
import com.study.compose.ui.common.utils.toIntOffset
import com.study.compose.ui.home.components.HomeActionIcon
import com.study.compose.ui.home.components.NavigationIcon
import com.study.compose.ui.home.components.ShrineDrawer
import com.study.compose.ui.home.components.TopHeader
import com.study.compose.ui.home.data.Product
import com.study.compose.ui.home.interactor.intent.HomeIntent
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.ui.home.view.ProductsContent
import com.study.compose.ui.home.viewmodel.HomeViewModel
import com.study.compose.ui.state.AppStateViewModel
import com.study.compose.ui.state.AppViewAction
import com.study.compose.ui.state.rememberAppState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    appStateViewModel: AppStateViewModel,
    onQrPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (Long) -> Unit
) {
    HomeScreen(
        viewModel = hiltViewModel(),
        onQrPressed = onQrPressed,
        onSearchPressed = onSearchPressed,
        onProductSelect = onProductSelect,
        appViewStateVM = appStateViewModel
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    appViewStateVM: AppStateViewModel,
    onQrPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.FetchProducts)
    }

    val homeViewState = viewModel.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val appState = rememberAppState()

    val scaffoldState = appState.scaffoldState

    Products(
        viewState = homeViewState.value,
        appViewStateVM = appViewStateVM,
        scaffoldState = scaffoldState,
        onQrPressed = onQrPressed,
        onSearchPressed = onSearchPressed,
        onProductSelect = onProductSelect,
        onAddedPress = { product, offset ->
            coroutineScope.launch {
                viewModel.processIntent(HomeIntent.AddCart(product = product, coordinate = offset))
            }
        },
        onProductAddedDone = {
            coroutineScope.launch {
                viewModel.processIntent(HomeIntent.ClearIdProductAdded)
            }
        },
        onCategorySelected = { category ->
            coroutineScope.launch {
                viewModel.processIntent(HomeIntent.SelectCategory(category))
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Products(
    viewState: HomeViewState,
    appViewStateVM: AppStateViewModel,
    scaffoldState: BackdropScaffoldState,
    onQrPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (Long) -> Unit,
    onAddedPress: (Product, Offset) -> Unit,
    onProductAddedDone: () -> Unit,
    onCategorySelected: (String) -> Unit,
) {
    var backdropRevealed by remember { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()
    var positionProductAdded by remember {
        mutableStateOf(Offset.Zero)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ShrineScaffold(
            topBar = {
                ShrineTopBar(
                    navIcon = {
                        NavigationIcon(
                            backdropRevealed = backdropRevealed,
                            onRevealed = { revealed ->
                                if (!scaffoldState.isAnimationRunning) {
                                    backdropRevealed = revealed
                                    scope.launch {
                                        appViewStateVM.process(AppViewAction.ShowBottomCart(!revealed))
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
                            com.study.compose.ui.common.R.drawable.ic_qr_scanner,
                            onPressed = onQrPressed
                        )
                    },
                    title = { TopHeader(backdropRevealed = backdropRevealed) }
                )
            },
            backLayerContent = {
                NavigationMenus(
                    backdropRevealed = backdropRevealed,
                    currentCategory = viewState.categoryMenuSelected,
                    categories = viewState.categories,
                    onCategorySelected = { category ->
                        // UNCOMMENT it if you want to conceal backdrop after select category
//                        if (!scaffoldState.isAnimationRunning) {
//                            backdropRevealed = false
//                            scope.launch {
//                                appViewStateVM.process(AppViewAction.ShowBottomCart(true))
//                                scaffoldState.conceal()
//                            }
                        onCategorySelected(category)
//                        }
                    }
                )
            },
            scaffoldState = scaffoldState
        ) {
            ProductsContent(
                onProductSelect = { product -> onProductSelect(product.id) },
                modifier = Modifier.padding(vertical = 56.dp),
                products = viewState.product.filteredProducts,
                onProductAddPress = { product: Product, coordinate: Offset ->
                    positionProductAdded = coordinate
                    onAddedPress(product, coordinate)
                },
                enableAdd = viewState.idProductAdded == null || positionProductAdded == Offset.Zero,
                screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
            )
        }

        if (viewState.idProductAdded != null && positionProductAdded != Offset.Zero) {
            val productAdded =
                viewState.product.filteredProducts.find { it.id == viewState.idProductAdded }
            if (productAdded != null) {
                AddedFlyableProduct(
                    productAdded = productAdded,
                    beginCoordinate = positionProductAdded,
                    onAdded = onProductAddedDone
                )
            }
        }
    }

}

@Composable
fun AddedFlyableProduct(
    productAdded: Product,
    beginCoordinate: Offset,
    onAdded: () -> Unit
) {
    val productSize = 36.dp
    val screenWidth =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() - productSize.toPx() }
    val screenHeight =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() - productSize.toPx() }

    val offset = remember { Animatable(beginCoordinate, Offset.VectorConverter) }

    LaunchedEffect(true) {
        offset.animateTo(
            targetValue = Offset(
                x = screenWidth,
                y = screenHeight
            ),
            animationSpec = tween(
                durationMillis = 500
            )
        )
        onAdded()
    }

    AsyncImage(
        modifier = Modifier
            .size(productSize)
            .offset { offset.value.toIntOffset() }
            .clip(CircleShape),
        model = productAdded.imageUrl,
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
    )
}


@Composable
fun NavigationMenus(
    backdropRevealed: Boolean,
    categories: List<String>,
    currentCategory: String,
    onCategorySelected: (String) -> Unit
) {
    ShrineDrawer(
        // This padding so useful. It prevent crash by BackdropScaffold caused by peekheight
        modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
        backdropRevealed = backdropRevealed,
        categories = categories,
        onMenuSelected = onCategorySelected,
        currentCategory = currentCategory,
    )
}


//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun HomeScreenPreview() {
//    ShrineComposeTheme {s
//        Products(onProductSelect = {})
//    }
//}