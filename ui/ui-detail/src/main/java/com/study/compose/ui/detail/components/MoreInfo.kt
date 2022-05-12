package com.study.compose.ui.detail.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.study.compose.ui.common.components.ShrineDivider
import com.study.compose.ui.common.theme.ShrineComposeTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoreDetail(
    modifier: Modifier = Modifier,
    sizes: List<Int> = listOf(1, 2, 3, 4, 5, 6),
    onSizeSelected: (size: Int) -> Unit,
    colors: List<Long> = listOf(0xFFA5E7E8, 0xFFDAD5D5, 0xFFF9D8AC),
    onColorSelected: (color: Long) -> Unit
) {
    var expandedState by remember { mutableStateOf(true) }

    Surface(color = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "More Details".uppercase(),
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight(500)
                )
                IconButton(onClick = { expandedState = !expandedState }) {
                    Crossfade(
                        targetState = expandedState,
                        animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
                    ) { expanded ->
                        if (expanded) {
                            Icon(
                                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_drop_down_24),
                                contentDescription = "MoreDetail"
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_drop_up_24),
                                contentDescription = "MoreDetail"
                            )
                        }

                    }
                }
            }
            AnimatedContent(
                targetState = expandedState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, 150)) with
                            fadeOut(animationSpec = tween(150)) using
                            SizeTransform { initialSize, targetSize ->
                                if (targetState) {
                                    keyframes {
                                        // Expand horizontally first.
                                        IntSize(targetSize.width, initialSize.height) at 100
                                        durationMillis = 200
                                    }
                                } else {
                                    keyframes {
                                        // Shrink vertically first.
                                        IntSize(initialSize.width, targetSize.height) at 100
                                        durationMillis = 200
                                    }
                                }
                            }

                }
            ) { expanded ->
                if (expanded) {
                    Column {
                        ShrineDivider()
                        SelectSize(size = sizes) { onSizeSelected(it) }
                        ShrineDivider(modifier = Modifier.padding(top = 10.dp))
                        SelectColor(colors = colors) { onColorSelected(it) }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SelectSize(
    modifier: Modifier = Modifier,
    size: List<Int>,
    selectedSize: Int = 1,
    onSizeSelected: (size: Int) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Select Size", style = MaterialTheme.typography.h6)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            size.forEach {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.5.dp,
                            color = Color(if (selectedSize == it) 0xFFBDBDBD else 0xFFFCB8AB),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(color = MaterialTheme.colors.surface)
                        .clickable { onSizeSelected(it) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        style = MaterialTheme.typography.body1,
                        fontSize = TextUnit(12f, TextUnitType.Sp),
                    )
                }
            }
        }
    }

}

@Composable
fun SelectColor(
    modifier: Modifier = Modifier,
    colors: List<Long>,
    selectedColor: Long = 0xFFA5E7E8,
    colorSelected: (color: Long) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Select Color",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight(500)
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            colors.forEach {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color(if (selectedColor == it) 0xFFBDBDBD else 0xFFFCB8AB),
                            shape = CircleShape
                        )
                        .background(color = Color(it))
                        .clickable { colorSelected(it) }
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectSizePreview() {
    ShrineComposeTheme {
        SelectSize(size = listOf(1, 2, 3, 4, 5)) {}
    }
}

@Preview
@Composable
fun SelectColorPreview() {
    ShrineComposeTheme {
        SelectColor(
            colors = listOf(0xFFA5E7E8, 0xFFDAD5D5, 0xFFF9D8AC),
            selectedColor = 0xFFA5E7E8
        ) {}
    }
}

@Preview
@Composable
fun MoreDetailPreview() {
    ShrineComposeTheme {
        MoreDetail(
            onSizeSelected = {},
            onColorSelected = {}
        )
    }
}
