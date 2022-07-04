package com.study.compose.ui.cart

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.study.compose.ui.cart.data.Cart
import com.study.compose.ui.cart.interactor.intent.CartIntent
import com.study.compose.ui.cart.interactor.state.CartState
import com.study.compose.ui.cart.interactor.state.CartViewState
import com.study.compose.ui.cart.viewmodel.CartVM
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.state.AppStateViewModel
import kotlin.math.min

@Composable
fun BottomCart(
    modifier: Modifier = Modifier,
    appStateViewModel: AppStateViewModel
) {
    BottomCart(
        modifier = modifier,
        viewModel = hiltViewModel(),
        appStateViewModel = appStateViewModel
    )
}

@Composable
fun BottomCart(
    modifier: Modifier = Modifier,
    viewModel: CartVM,
    appStateViewModel: AppStateViewModel
) {
    val carts = viewModel.cartLazyFlow.collectAsLazyPagingItems()
    val viewState by viewModel.viewState.collectAsState()
    val appViewState by appStateViewModel.state.collectAsState()

    BottomCart(
        modifier = modifier,
        viewState = viewState,
        hidden = !appViewState.showBottomCart,
        carts = carts
    )
}

@Composable
fun BottomCart(
    modifier: Modifier = Modifier,
    viewState: CartViewState,
    hidden: Boolean,
    carts: LazyPagingItems<Cart>
) {
    val config = LocalConfiguration.current
    val cartsChange = viewState.cartsChange
    BottomCart(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        maxHeight = config.screenHeightDp.dp,
        maxWidth = config.screenWidthDp.dp,
        carts = carts,
        hidden = hidden
    )
}

@Composable
fun BottomCart(
    modifier: Modifier,
    hidden: Boolean = false,
    carts: LazyPagingItems<Cart>,
    maxWidth: Dp,
    maxHeight: Dp
) {

    var expanded by remember { mutableStateOf(false) }
    Row(modifier = modifier) {

        // Add updateTransition
        val cartTransition = updateTransition(
            targetState = when {
                hidden -> CartState.Hidden
                expanded -> CartState.Expanded
                else -> CartState.Collapsed
            },
            label = "CartTransition",
        )

        val cartXOffset by cartTransition.animateDp(label = "CartWidth", transitionSpec = {
            when {
                CartState.Expanded isTransitioningTo CartState.Collapsed -> tween(
                    450,
                    delayMillis = 150
                )
                CartState.Collapsed isTransitioningTo CartState.Expanded -> tween(200)
                else -> tween(400)
            }
        }) { state ->
            when (state) {
                CartState.Expanded -> 0.dp
                CartState.Hidden -> maxWidth
                CartState.Collapsed -> {
                    val size = min(2, carts.itemCount)
                    // Collapse Cart Size paddingStart: 24, CartIcon: 40, each CartItem: 40, paddingEnd 16
                    // 16 * size: Spacer
                    val width = 24 + 40 * (size + 1) + 16 * size + 16
                    (maxWidth.value - (width)).dp
                }
            }
        }

        val cartHeightOffset by cartTransition.animateDp(
            label = "CartHeight",
            transitionSpec = {
                when {
                    CartState.Expanded isTransitioningTo CartState.Collapsed -> tween(
                        durationMillis = 300
                    )
                    else -> tween(500)
                }
            }
        ) { state ->
            if (state == CartState.Expanded) maxHeight else 56.dp
        }

        val cornerSize by cartTransition.animateDp(label = "cartCorner", transitionSpec = {
            when {
                CartState.Expanded isTransitioningTo CartState.Collapsed -> tween(
                    durationMillis = 433,
                    delayMillis = 67
                )
                else -> tween(durationMillis = 150)
            }
        }) {
            if (it == CartState.Expanded) 0.dp else 24.dp
        }

        Surface(
            modifier = Modifier
                .offset(x = cartXOffset)
                .height(cartHeightOffset)
                .width(maxWidth - cartXOffset),
            color = MaterialTheme.colors.secondary,
            shape = CutCornerShape(topStart = cornerSize),
            elevation = 8.dp
        ) {
            if (expanded) {
                ExpandedCarts(carts = carts) {
                    expanded = false
                }
            } else {
                CollapsedCart(carts = carts) {
                    expanded = true
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsedCart(
    carts: LazyPagingItems<Cart>,
    maxCartCount: Int = 2,
    onTap: () -> Unit,
) {
    val size = carts.itemCount
    Row(
        modifier = Modifier
            .clickable { onTap() }
            .fillMaxWidth()
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Shopping Cart",
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            items(
                carts.itemSnapshotList.items.subList(
                    0,
                    if (size < maxCartCount) size else maxCartCount
                ).reversed()
            ) { cart ->
                CollapseCartItem(cart = cart, modifier = Modifier.animateItemPlacement(
                    tween(durationMillis = 250)
                ))
            }
        }
    }
}

@Composable
fun CollapseCartItem(modifier: Modifier = Modifier, cart: Cart) {
    AsyncImage(
        model = cart.imageUrl,
        contentDescription = "Collapse Cart Item ${cart.productId}",
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .then(modifier)

    )
}

@Composable
fun CartHeader(cartSize: Int, onTap: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onTap() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow Down"
            )
        }

        Text(text = "Cart".uppercase(), style = MaterialTheme.typography.subtitle1)

        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "$cartSize items".uppercase())
    }
}

@Composable
fun ExpandedCarts(carts: LazyPagingItems<Cart>, onCollapse: () -> Unit) {
    Surface(color = MaterialTheme.colors.secondary) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            CartHeader(carts.itemCount) {
                onCollapse()
            }
            LazyColumn(
                Modifier
                    .padding(top = 56.dp)
                    .fillMaxWidth()
            ) {
                items(carts) { cart ->
                    if (cart != null) {
                        CartItem(cart = cart)
                    }
                }
            }
        }
    }

}

@Composable
fun CartItem(
    cart: Cart
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(120.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }, Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "Remove Cart"
            )
        }
        Column(Modifier.fillMaxWidth()) {
            Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    model = cart.imageUrl,
                    contentDescription = "Cart ${cart.productId}",
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(Modifier.padding(end = 16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = cart.category)
                        Text(text = "$${cart.price}")
                    }
                    Text(text = cart.title)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.secondary) {
            CartItem(
                cart = Cart(
                    productId = 0,
                    title = "Vagabond sack",
                    price = 120f,
                )
            )
        }
    }
}

//@Preview
//@Composable
//fun CollapsedCartPreview() {
//    ShrineComposeTheme {
//        Surface(color = MaterialTheme.colors.secondary) {
//            CollapsedCart(
//                listOf(
//                    Cart(
//                        productId = 0,
//                        title = "Vagabond sack",
//                        price = 120f,
//                    )
//                )
//            ) {}
//        }
//    }
//}

@Preview
@Composable
fun CartHeaderPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.secondary) {
            CartHeader(5) {

            }
        }
    }
}


//@Preview
//@Composable
//private fun ExpandedCartItemsPreview() {
//    ShrineComposeTheme {
//        ExpandedCarts(
//            listOf(
//                Cart(
//                    productId = 0,
//                    title = "Vagabond sack",
//                    price = 120f,
//                )
//            )
//        ) { }
//    }
//}
