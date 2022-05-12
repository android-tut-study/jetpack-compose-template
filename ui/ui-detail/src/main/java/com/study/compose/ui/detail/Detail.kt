package com.study.compose.ui.detail

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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.study.compose.ui.common.components.ShrineDivider
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.components.AlsoLikes
import com.study.compose.ui.detail.components.DetailHeader
import com.study.compose.ui.detail.components.MoreDetail
import com.study.compose.ui.detail.components.ProductInfo
import com.study.compose.ui.detail.viewmodel.DetailViewModel
import kotlin.math.max
import kotlin.math.min

private val MinTitleOffset = 56.dp
private val ExpandedImageHeight = 180.dp
private val CollapsedImageHeight = 60.dp
private val TitleHeight = 110.dp
private val ScreenPadding = 12.dp

@Composable
fun Detail(
    onCartAddPressed: () -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    Detail(
        viewModel = hiltViewModel(),
        onCartAddPressed = onCartAddPressed,
        onClosePressed = onClosePressed,
        onFavoritePressed = onFavoritePressed
    )
}

@Composable
fun Detail(
    viewModel: DetailViewModel,
    onCartAddPressed: () -> Unit = {},
    onClosePressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.surface) {
            val scrollState = rememberScrollState(0)
            val scroll = scrollState.value
            val collapseRange = with(LocalDensity.current) { (ExpandedImageHeight).toPx() }
            val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)

            Box(modifier = Modifier.fillMaxSize()) {
                ProductImage(scroll = scrollState.value)
                Body(scrollState, scroll)
                ConcealedTitle(scroll = scroll)
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
fun ConcealedTitle(modifier: Modifier = Modifier, scroll: Int) {
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
                    Image(
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 10.dp, end = ScreenPadding)
                            .size(CollapsedImageHeight)
                            .clip(CircleShape)
                            .align(Alignment.BottomEnd),
                        painter = painterResource(id = R.drawable.fake),
                        contentDescription = "Fake1",
                        contentScale = ContentScale.Crop,
                    )
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
fun Title(scroll: Int) {
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
        Image(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 10.dp, end = ScreenPadding)
                .size(CollapsedImageHeight)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .alpha(1 - alpha),
            painter = painterResource(id = R.drawable.fake),
            contentDescription = "Fake2",
            contentScale = ContentScale.Crop,
        )

    }
}

@Composable
fun Body(scrollState: ScrollState, scroll: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(ExpandedImageHeight))
        Title(scroll)
        MoreDetail(
            modifier = Modifier.padding(horizontal = ScreenPadding),
            onSizeSelected = {},
            onColorSelected = {})
        Spacer(modifier = Modifier.height(10.dp))
        AddToCart()
        Spacer(modifier = Modifier.height(20.dp))
        AlsoLikes(modifier = Modifier.padding(horizontal = ScreenPadding))
        Spacer(modifier = Modifier.height(64.dp))
    }

}

@Composable
fun ProductImage(modifier: Modifier = Modifier, scroll: Int) {
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
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.fake),
            contentDescription = "Fake",
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center,
        )
    }
}

@Composable
fun CollapsingImageLayout(
    modifier: Modifier = Modifier,
    collapseFraction: Float,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measureables, constraints ->
        check(measureables.size == 1)
        // calculate image size by collapseFraction
        val imageMaxSize = min(ExpandedImageHeight.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageHeight.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val placeable = measureables[0].measure(Constraints.fixed(constraints.maxWidth, imageWidth))

        // calculate image coordination
        val imageY = lerp(0.dp, 56.dp, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2,
            constraints.maxWidth - imageWidth - 10.dp.roundToPx(),
            collapseFraction
        )

        layout(width = constraints.maxWidth, height = imageY + imageWidth) {
            placeable.placeRelative(imageX, imageY)
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


@Preview
@Composable
private fun DetailPreview() {
    Detail()
}