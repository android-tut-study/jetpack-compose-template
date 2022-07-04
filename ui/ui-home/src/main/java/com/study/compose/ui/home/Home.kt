package com.study.compose.ui.home

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.study.compose.ui.common.components.ShrineScaffold
import com.study.compose.ui.common.components.ShrineTopBar
import com.study.compose.ui.common.utils.toIntOffset
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.Product
import com.study.compose.ui.home.interactor.intent.HomeIntent
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.ui.home.view.ProductsContent
import com.study.compose.ui.home.viewmodel.HomeViewModel
import com.study.compose.ui.state.AppStateViewModel
import com.study.compose.ui.state.rememberAppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (Long) -> Unit
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
    onProductSelect: (Long) -> Unit
) {
    val homeViewState = viewModel.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    // TODO Test
    LaunchedEffect(true) {
        viewModel.processIntent(HomeIntent.FetchProducts)
    }
    Products(
        viewState = homeViewState.value,
        onFilterPressed = onFilterPressed,
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

        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Products(
    viewState: HomeViewState,
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onProductSelect: (Long) -> Unit,
    onAddedPress: (Product, Offset) -> Unit,
    onProductAddedDone: () -> Unit
) {
    val appState = rememberAppState()
    val appViewStateVM: AppStateViewModel = viewModel()
    val scaffoldState = appState.scaffoldState
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
                onProductSelect = { product -> onProductSelect(product.id) },
                modifier = Modifier.padding(vertical = 56.dp),
                products = viewState.product.products,
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
                viewState.product.products.find { it.id == viewState.idProductAdded }
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
fun NavigationMenus(backdropRevealed: Boolean) {
    ShrineDrawer(
        backdropRevealed = backdropRevealed,
        // This padding so useful. It prevent crash by BackdropScaffold caused by peekheight
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