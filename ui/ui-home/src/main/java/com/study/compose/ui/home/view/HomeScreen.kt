package com.study.compose.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.study.compose.ui.common.R
import com.study.compose.ui.home.data.Product

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    onProductSelect: (product: Product) -> Unit,
    onProductAddPress: (Product, coordinate: Offset) -> Unit,
    products: List<Product> = emptyList(),
    enableAdd: Boolean = true,
    screenWidth: Int,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .horizontalScroll(rememberScrollState())
        ) {
            StaggerProduct(
                dividerSpace = with(LocalDensity.current) { 30.dp.roundToPx() },
                offsetInColumn = with(LocalDensity.current) { 20.dp.roundToPx() },
                screenWidth = screenWidth
            ) {
                products.forEach {
                    ProductChip(
                        product = it,
                        onClick = onProductSelect,
                        onAddPress = onProductAddPress,
                        enableAdd = enableAdd
                    )
                }
            }
        }

    }
}

@Composable
fun StaggerProduct(
    modifier: Modifier = Modifier,
    dividerSpace: Int,
    offsetInColumn: Int,
    screenWidth: Int,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measureables, constraints ->
        val baseConstraints = Constraints.fixedWidth(width = screenWidth)

        val cellConstraints = measureables.map {
            Constraints.fixedWidth(width = baseConstraints.maxWidth / 2)
        }
        var totalWidth = 0
        val maxHeight = constraints.maxHeight
        val baseHeight = maxHeight / 2
        val placeables = mutableListOf<Placeable>()
        val size = measureables.size

        // Flag used to determine still have bottom space/slot after place item to previous column
        var previousSpaceExist = false

        // Calculate placeable items
        for (index in 0 until size step 2) {
            val measurable = measureables[index]
            val currentPlaceable = measurable.measure(cellConstraints[index])
            val currentHeight = currentPlaceable.height
            placeables.add(currentPlaceable)

            var nextPlaceable: Placeable? = null
            if (index + 1 < size) {
                nextPlaceable = measureables[index + 1].measure(cellConstraints[index + 1])
            }

            // Beginning item
            if (totalWidth == 0) {
                totalWidth += currentPlaceable.width + dividerSpace
                if (currentHeight < baseHeight) {
                    previousSpaceExist = true
                }
            } else {
                if (previousSpaceExist) {
                    totalWidth += if (currentHeight >= baseHeight) {
                        currentPlaceable.width + dividerSpace
                    } else {
                        offsetInColumn
                    }
                    previousSpaceExist = false
                } else {
                    totalWidth += currentPlaceable.width + dividerSpace
                    if (currentHeight < baseHeight) {
                        previousSpaceExist = true
                    }
                }
            }

            nextPlaceable?.let { next ->
                placeables.add(next)
                val nextHeight = next.height

                if (previousSpaceExist) {
                    totalWidth += if (nextHeight >= baseHeight) {
                        next.width + dividerSpace
                    } else {
                        offsetInColumn
                    }
                    previousSpaceExist = false
                } else {
                    if (nextHeight < baseHeight) {
                        previousSpaceExist = true
                    }
                    totalWidth += next.width + dividerSpace
                }
            }
        }

        // remove redundant divider at the end
        totalWidth -= dividerSpace

        previousSpaceExist = false
        val placeableSize = placeables.size
        layout(width = totalWidth, height = constraints.maxHeight) {
            var xPosition = 0
            for (index in 0 until placeableSize step 2) {
                val currentPlaceable = placeables[index]
                var nextPlaceable: Placeable? = null
                if (index + 1 < placeableSize) {
                    nextPlaceable = placeables[index + 1]
                }
                val currentHeight = currentPlaceable.height

                if (currentHeight >= baseHeight) {
                    if (previousSpaceExist) {
                        xPosition += currentPlaceable.width + dividerSpace
                        previousSpaceExist = false
                    }
                    currentPlaceable.placeRelative(xPosition, (maxHeight - currentHeight) / 2)
                    xPosition += currentPlaceable.width + dividerSpace

                    nextPlaceable?.let { next ->
                        if (next.height >= baseHeight) {
                            next.placeRelative(xPosition, (maxHeight - next.height) / 2)
                            xPosition += next.width + dividerSpace
                        } else {
                            next.placeRelative(xPosition, 0)
                            previousSpaceExist = true
                        }
                    }
                } else {
                    if (previousSpaceExist) {
                        xPosition += offsetInColumn
                        currentPlaceable.placeRelative(xPosition, baseHeight)
                        xPosition += currentPlaceable.width + dividerSpace
                        nextPlaceable?.let { next ->
                            if (next.height >= baseHeight) {
                                next.placeRelative(xPosition, (maxHeight - next.height) / 2)
                                xPosition += next.width + dividerSpace
                                previousSpaceExist = false
                            } else {
                                next.placeRelative(xPosition, 0)
                                previousSpaceExist = true
                            }
                        }

                    } else {
                        currentPlaceable.placeRelative(xPosition, 0)
                        nextPlaceable?.let { next ->
                            if (next.height >= baseHeight) {
                                next.placeRelative(
                                    xPosition + next.width,
                                    (maxHeight - next.height) / 2
                                )
                                xPosition += 2 * (next.width + dividerSpace)
                            } else {
                                xPosition += offsetInColumn
                                next.placeRelative(xPosition, baseHeight)
                                xPosition += next.width + dividerSpace
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductChip(
    product: Product,
    onClick: (product: Product) -> Unit,
    onAddPress: (Product, coordinate: Offset) -> Unit,
    enableAdd: Boolean
) {

    var positionInRoot by remember { mutableStateOf(Offset.Zero) }

    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .clickable { onClick(product) }
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = product.imageUrl,
                    contentDescription = "${product.id}",
                    contentScale = ContentScale.FillWidth,
                )
                Image(
                    painter = painterResource(id = R.drawable.fake_brand),
                    contentDescription = "fake brand",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(36.dp)
                        .offset(y = 12.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W500)
            )
        }
        IconButton(
            enabled = enableAdd,
            onClick = { onAddPress(product, positionInRoot) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .clip(CircleShape)
                .onGloballyPositioned { coordinates ->
                    positionInRoot = coordinates.positionInRoot()
                }
        ) {
            Image(
                painter = painterResource(
                    id = if (product.isAdded) R.drawable.ic_remove_cart_24 else R.drawable.ic_add_cart_24
                ),
                contentDescription = "Cart",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


//@Preview(widthDp = 480, heightDp = 560)
//@Composable
//private fun StaggerLayoutPreview() {
//    ShrineComposeTheme {
//        StaggerProduct(
//            dividerSpace = with(LocalDensity.current) { 20.dp.roundToPx() },
//            offsetInColumn = with(LocalDensity.current) { 20.dp.roundToPx() }) {
//            SampleCartItems.forEach {
//                ProductChip(it, onClick = {})
//            }
//        }
//    }
//}
