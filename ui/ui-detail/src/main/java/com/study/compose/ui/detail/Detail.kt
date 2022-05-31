package com.study.compose.ui.detail

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.study.compose.ui.common.components.ShrineDivider
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.components.AlsoLikes
import com.study.compose.ui.detail.components.DetailHeader
import com.study.compose.ui.detail.components.MoreDetail
import com.study.compose.ui.detail.components.ProductInfo
import com.study.compose.ui.detail.data.ProductDetail
import com.study.compose.ui.detail.interactor.intent.DetailIntent
import com.study.compose.ui.detail.interactor.state.CurrentProduct
import com.study.compose.ui.detail.interactor.state.DetailViewState
import com.study.compose.ui.detail.viewmodel.DetailViewModel
import com.study.compose.ui.state.AppStateViewModel
import com.study.compose.ui.state.rememberAppState

private val MinTitleOffset = 56.dp
private val ExpandedImageHeight = 180.dp
private val CollapsedImageHeight = 60.dp
private val TitleHeight = 110.dp
private val ScreenPadding = 12.dp

@Composable
fun Detail(
    productId: Int,
    onCartAddPressed: () -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    val id = remember { productId }
    Detail(
        productId = id,
        viewModel = hiltViewModel(),
        onCartAddPressed = onCartAddPressed,
        onClosePressed = onClosePressed,
        onFavoritePressed = onFavoritePressed
    )
}

@Composable
fun Detail(
    productId: Int,
    viewModel: DetailViewModel,
    onCartAddPressed: () -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    LaunchedEffect(true) {
        viewModel.processIntent(DetailIntent.Initial(productId = productId))
    }
    val viewState by viewModel.viewState.collectAsState()
    Detail(
        viewState = viewState,
        onCartAddPressed = onCartAddPressed,
        onClosePressed = onClosePressed,
        onFavoritePressed = onFavoritePressed,
    )
}

@Composable
fun Detail(
    viewState: DetailViewState,
    onCartAddPressed: () -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    val scrollState = rememberScrollState(0)
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.surface) {
            val scroll = scrollState.value
            val collapseRange = with(LocalDensity.current) { (ExpandedImageHeight).toPx() }
            val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)

            Box(modifier = Modifier.fillMaxSize()) {
                ProductImage(
                    scroll = scrollState.value,
                    currentProduct = viewState.currentProduct
                )
                Body(scrollState, scroll, currentProduct = viewState.currentProduct, alsoLikes = viewState.products)
                ConcealedTitle(scroll = scroll, currentProduct = viewState.currentProduct)
                DetailHeader(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.surface.copy(
                                alpha = (collapseFraction * 1.4f).coerceIn(0f, 1f)
                            )
                        ),
                    onNavigationPressed = onClosePressed,
                    onFavoritePressed = onFavoritePressed,
                    onCartAddPressed = onCartAddPressed
                )
            }
        }
    }
}

@Composable
fun ConcealedTitle(
    modifier: Modifier = Modifier,
    scroll: Int,
    currentProduct: ProductDetail?
) {
    val maxOffset = with(LocalDensity.current) { (ExpandedImageHeight).toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
    if (offset == minOffset) {
        Surface(color = MaterialTheme.colors.surface, modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = offset
            }
            .then(modifier)
        ) {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    ProductInfo(
                        modifier = Modifier
                            .height(TitleHeight)
                            .padding(horizontal = ScreenPadding)
                    )
                    val concealedImageModifier = Modifier
                        .padding(bottom = 20.dp, top = 10.dp, end = ScreenPadding)
                        .size(CollapsedImageHeight)
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd)
                    if (currentProduct != null) {
                        AsyncImage(
                            modifier = concealedImageModifier,
                            model = currentProduct.imageUrl,
                            contentDescription = "Fake1",
                            contentScale = ContentScale.Inside,
                        )
                    } else {
                        // TODO Change to PlaceHolder
                        Image(
                            modifier = concealedImageModifier,
                            painter = painterResource(id = com.study.compose.ui.common.R.drawable.fake),
                            contentDescription = "Fake1",
                            contentScale = ContentScale.Inside,
                        )
                    }
                }
                ShrineDivider(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .padding(horizontal = ScreenPadding)
                )
            }
        }
    }
}

@Composable
fun Title(scroll: Int, currentProduct: ProductDetail?) {
    val maxOffset = with(LocalDensity.current) { (ExpandedImageHeight).toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
    val alpha = (offset / maxOffset).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ProductInfo(
            Modifier
                .height(TitleHeight)
                .padding(horizontal = ScreenPadding)
        )

        val titleImageModifier = Modifier
            .padding(bottom = 20.dp, top = 10.dp, end = ScreenPadding)
            .size(CollapsedImageHeight)
            .clip(CircleShape)
            .align(Alignment.BottomEnd)
            .alpha(1 - alpha)
        if (currentProduct != null) {
            AsyncImage(
                modifier = titleImageModifier,
                model = currentProduct.imageUrl,
                contentDescription = "Fake2",
                contentScale = ContentScale.Inside,
            )
        } else {
            // TODO Change to PlaceHolder
            Image(
                modifier = titleImageModifier,
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.fake),
                contentDescription = "Fake2",
                contentScale = ContentScale.Inside,
            )
        }

    }
}

@Composable
fun Body(scrollState: ScrollState, scroll: Int, currentProduct: ProductDetail?, alsoLikes: List<ProductDetail>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(ExpandedImageHeight))
        Title(scroll, currentProduct)
        MoreDetail(
            modifier = Modifier.padding(horizontal = ScreenPadding),
            onSizeSelected = {},
            onColorSelected = {})
        Spacer(modifier = Modifier.height(10.dp))
        AddToCart()
        Spacer(modifier = Modifier.height(20.dp))
        AlsoLikes(modifier = Modifier.padding(horizontal = ScreenPadding), items = alsoLikes)
        Spacer(modifier = Modifier.height(64.dp))
    }

}

@Composable
fun ProductImage(
    modifier: Modifier = Modifier,
    scroll: Int,
    currentProduct: ProductDetail? = null
) {
    val collapseRange = with(LocalDensity.current) { (ExpandedImageHeight).toPx() }
    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 0.4f)
    val offset = -scroll * collapseFraction
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ExpandedImageHeight)
            .alpha(1f - collapseFraction)
            .graphicsLayer {
                translationY = offset
            },
        contentAlignment = Alignment.Center
    ) {
        if (currentProduct != null) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = currentProduct.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center,
            )
        } else {
            // TODO Change to PlaceHolder
            Image(
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.fake),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Place Holder",
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
            )
        }
    }
}

@Composable
fun AddToCart() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(56.dp),
        elevation = ButtonDefaults.elevation(4.dp),
        shape = CutCornerShape(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_add_cart_24),
                contentDescription = "AddToCart"
            )
            Text(text = "Add To Cart".uppercase())
        }
    }
}


//@Preview
//@Composable
//private fun DetailPreview() {
//    Detail()
//}