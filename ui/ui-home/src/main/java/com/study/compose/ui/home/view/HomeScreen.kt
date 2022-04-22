package com.study.compose.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.data.Cart
import com.study.compose.ui.home.data.SampleCartItems

@Composable
fun ProductsContent(
    onProductSelect: (cart: Cart) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 20.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            StaggeredProductGrid(
                screenWidth = LocalConfiguration.current.screenWidthDp.dp,
                screenHeight = LocalConfiguration.current.screenHeightDp.dp
            ) {
                SampleCartItems.forEach {
                    ProductChip(cart = it, onClick = onProductSelect)
                }
            }
        }


    }
}

@Composable
fun StaggeredProductGrid(
    modifier: Modifier = Modifier,
    rows: Int = 2,
    screenHeight: Dp,
    screenWidth: Dp,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measureables, constraints ->
        var totalWidth = 0
        val screenWidthPx = screenWidth.roundToPx()
        val screenHeightPx = screenHeight.roundToPx()

        val baseConstraints = Constraints.fixed(
            width = screenWidthPx / rows,
            height = screenHeightPx / rows,
        )

        val cellConstraints = measureables.mapIndexed { index, _ ->
            val spanHeight = if (index % 3 == 2) 2 else 1

            Constraints.fixed(
                width = baseConstraints.maxWidth * spanHeight,
                height = baseConstraints.maxHeight * spanHeight
            )
        }

        val placeables = measureables.mapIndexed { index, measurable ->
            val spanHeight = if (index % 3 == 2) 2 else 1
            val placeable = measurable.measure(cellConstraints[index])
            if (spanHeight == 2 || index % 3 == 0) {
                totalWidth += placeable.width
            }
            placeable
        }
        val maxHeight = constraints.maxHeight

        val width = totalWidth.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        layout(width, maxHeight) {
            var x = 0
            placeables.forEachIndexed { index, placeable ->
                val y = if (index % 3 == 1) maxHeight / 2 else 0
                placeable.placeRelative(
                    x = x,
                    y = y
                )

                if (index % 3 != 0) {
                    x += placeable.width
                }
            }
        }
    }
}

@Composable
fun ProductChip(cart: Cart, onClick: (cart: Cart) -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .clickable { onClick(cart) }
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = cart.photoResId),
                contentDescription = "Chip",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = cart.vendor.name, style = MaterialTheme.typography.subtitle1)
            Text(text = "$${cart.price}", style = MaterialTheme.typography.caption)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductChipPreview() {
    ShrineComposeTheme {
        ProductChip(SampleCartItems[0]) {}
    }
}

@Preview
@Composable
fun StaggeredProductGridPreview() {
    ShrineComposeTheme {
        StaggeredProductGrid(
            screenWidth = LocalConfiguration.current.screenWidthDp.dp,
            screenHeight = LocalConfiguration.current.screenHeightDp.dp,
        ) {
            SampleCartItems.forEach {
                ProductChip(it, onClick = {})
            }
        }
    }
}