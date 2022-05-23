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
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.data.Cart
import com.study.compose.ui.home.data.SampleCartItems

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    onProductSelect: (cart: Cart) -> Unit
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
                dividerSpace = with(LocalDensity.current) { 20.dp.roundToPx() }
            ) {
                SampleCartItems.forEach {
                    ProductChip(cart = it, onClick = onProductSelect)
                }
            }
        }

    }
}

@Composable
fun StaggerProduct(
    modifier: Modifier = Modifier,
    dividerSpace: Int,
    content: @Composable () -> Unit
) {
    val screenWidth =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
//    val offsetInColumn = with(LocalDensity.current) { 20.dp.roundToPx() }

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
                    if (currentHeight >= baseHeight) {
                        totalWidth += currentPlaceable.width + dividerSpace
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
                    if (nextHeight >= baseHeight) {
                        totalWidth += next.width + dividerSpace
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

        previousSpaceExist = false
        val placeableSize = placeables.size
        // remove redundant divider at the end
        totalWidth -= dividerSpace

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
                    currentPlaceable.placeRelative(xPosition, maxHeight - currentHeight)
                    xPosition += currentPlaceable.width + dividerSpace

                    nextPlaceable?.let { next ->
                        if (next.height >= baseHeight) {
                            next.placeRelative(xPosition, maxHeight - next.height)
                            xPosition += next.width + dividerSpace
                        } else {
                            next.placeRelative(xPosition, 0)
                            previousSpaceExist = true
                        }
                    }
                } else {
                    if (previousSpaceExist) {
                        currentPlaceable.placeRelative(xPosition, baseHeight)
                        xPosition += currentPlaceable.width + dividerSpace
                        nextPlaceable?.let { next ->
                            if (next.height >= baseHeight) {
                                next.placeRelative(xPosition, maxHeight - next.height)
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
                                next.placeRelative(xPosition + next.width, maxHeight - next.height)
                                xPosition += 2 * (next.width + dividerSpace)
                            } else {
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


@Preview(widthDp = 480, heightDp = 560)
@Composable
private fun StaggerLayoutPreview() {
    ShrineComposeTheme {
        StaggerProduct(dividerSpace = with(LocalDensity.current) { 20.dp.roundToPx() }) {
            SampleCartItems.forEach {
                ProductChip(it, onClick = {})
            }
        }
    }
}
