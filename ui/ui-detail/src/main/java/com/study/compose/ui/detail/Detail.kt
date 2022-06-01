package com.study.compose.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.study.compose.ui.common.components.ShrineDivider
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.components.AlsoLikes
import com.study.compose.ui.detail.components.DetailHeader
import com.study.compose.ui.detail.components.MoreDetail
import com.study.compose.ui.detail.components.ProductInfo
import com.study.compose.ui.detail.data.ProductDetail
import com.study.compose.ui.detail.interactor.intent.DetailIntent
import com.study.compose.ui.detail.interactor.state.DetailViewState
import com.study.compose.ui.detail.viewmodel.DetailViewModel

private val MinTitleOffset = 56.dp
private val ExpandedImageHeight = 180.dp
private val CollapsedImageHeight = 60.dp
private val TitleHeight = 110.dp
private val ScreenPadding = 12.dp

@Composable
fun Detail(
    productId: Int,
    onOtherDetailPressed: (Int) -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    val id = remember { productId }
    Detail(
        productId = id,
        viewModel = hiltViewModel(),
        onOtherDetailPressed = onOtherDetailPressed,
        onClosePressed = onClosePressed,
        onFavoritePressed = onFavoritePressed
    )
}

@Composable
fun Detail(
    productId: Int,
    viewModel: DetailViewModel,
    onOtherDetailPressed: (Int) -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    LaunchedEffect(true) {
        viewModel.processIntent(DetailIntent.Initial(productId = productId))
    }
    val viewState by viewModel.viewState.collectAsState()
    Detail(
        viewState = viewState,
        onOtherDetailPressed = onOtherDetailPressed,
        onClosePressed = onClosePressed,
        onFavoritePressed = onFavoritePressed,
    )
}

@Composable
fun Detail(
    viewState: DetailViewState,
    onOtherDetailPressed: (Int) -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    val scrollState = rememberScrollState(0)
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.surface) {
            val scroll = scrollState.value

            Box(modifier = Modifier.fillMaxSize()) {
                ProductImage(
                    scroll = scrollState.value,
                    currentProduct = viewState.currentProduct
                )
                Body(
                    scrollState,
                    currentProduct = viewState.currentProduct,
                    alsoLikes = viewState.products,
                    onAlsoPressed = { also -> onOtherDetailPressed(also.id) }
                )
                ConcealedTitle(scroll = scroll, currentProduct = viewState.currentProduct)
                DetailHeader(
                    scroll = scroll,
                    onNavigationPressed = onClosePressed,
                    onCartAddPressed = {},
                    onFavoritePressed = onFavoritePressed,
                    currentProduct = viewState.currentProduct
                )
            }
        }
    }
}

@Composable
fun DetailHeader(
    scroll: Int,
    onNavigationPressed: () -> Unit = {},
    onCartAddPressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {},
    currentProduct: ProductDetail?
) {
    val collapseRange = with(LocalDensity.current) { ExpandedImageHeight.toPx() }
    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)

    // calculate product image alpha
    val offset = (collapseRange - scroll).coerceIn(0f, collapseRange)
    val alpha = (offset / collapseRange).coerceIn(0f, 1f)

    DetailHeader(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface.copy(
                    alpha = (collapseFraction * 1.4f).coerceIn(0f, 1f)
                )
            ),
        onNavigationPressed = onNavigationPressed,
        onCartAddPressed = onCartAddPressed,
        onFavoritePressed = onFavoritePressed,
        productDetail = currentProduct,
        productImageModifier = Modifier.alpha(1 - alpha)
    )
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
                ProductInfo(
                    modifier = Modifier
                        .padding(horizontal = ScreenPadding),
                    productDetail = currentProduct
                )
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
fun Title(currentProduct: ProductDetail?) {
    ProductInfo(
        Modifier
            .padding(horizontal = ScreenPadding)
            .fillMaxWidth(),
        productDetail = currentProduct
    )
}

@Composable
fun Body(
    scrollState: ScrollState,
    currentProduct: ProductDetail?,
    alsoLikes: List<ProductDetail>,
    onAlsoPressed: (ProductDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(ExpandedImageHeight))
        Title(currentProduct)
        MoreDetail(
            modifier = Modifier.padding(horizontal = ScreenPadding),
            onSizeSelected = {},
            onColorSelected = {})
        Spacer(modifier = Modifier.height(10.dp))
        AddToCart()
        Spacer(modifier = Modifier.height(20.dp))
        AlsoLikes(
            modifier = Modifier.padding(horizontal = ScreenPadding),
            items = alsoLikes,
            onAlsoPressed = onAlsoPressed
        )
        Spacer(modifier = Modifier.height(64.dp))
    }

}

@Composable
fun ProductImage(
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