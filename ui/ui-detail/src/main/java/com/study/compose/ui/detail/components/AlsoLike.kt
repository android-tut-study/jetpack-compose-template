package com.study.compose.ui.detail.components

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.study.compose.ui.common.components.ShrineDivider
import com.study.compose.ui.detail.data.ProductDetail

@Composable
fun AlsoLikes(
    modifier: Modifier = Modifier,
    items: List<ProductDetail>
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .then(modifier)
    ) {
        ShrineDivider()
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "You’ll also like",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight(500)
        )
        Spacer(modifier = Modifier.height(10.dp))

        AlsoLikeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            dividerSpace = with(LocalDensity.current) { 20.dp.roundToPx() }
        ) {
            items.forEach { item ->
                AsyncImage(
                    model =  item.imageUrl,
                    contentDescription = "Also ${item.id}",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.BottomCenter
                )
            }
        }
    }
}

@Composable
fun AlsoLikeLayout(
    modifier: Modifier,
    dividerSpace: Int,
    content: @Composable () -> Unit
) {
    val screenWidth =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
    Layout(modifier = modifier, content = content) { measureables, constraints ->
        var totalWidth = 0
        var maxHeight = 0

        val baseConstraints = Constraints.fixedWidth(width = screenWidth)

        val cellConstraints = measureables.map {
            Constraints.fixedWidth(width = baseConstraints.maxWidth / 2)
        }

        val placeables = measureables.mapIndexed { index, measureable ->
            val placeable = measureable.measure(cellConstraints[index])
            totalWidth += placeable.width

            if (maxHeight < placeable.height) {
                maxHeight = placeable.height
            }
            placeable
        }

        totalWidth += (placeables.size - 1) * dividerSpace
        val width = totalWidth.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        layout(width = width, height = maxHeight) {
            var x = 0
            placeables.forEach { placeable ->
                val y = maxHeight - placeable.height
                placeable.placeRelative(x = x, y = y)
                x += placeable.width + dividerSpace
            }
        }
    }
}

//@Preview
//@Composable
//fun AlsoLikesPreview() {
//    AlsoLikes(listOf<>())
//}