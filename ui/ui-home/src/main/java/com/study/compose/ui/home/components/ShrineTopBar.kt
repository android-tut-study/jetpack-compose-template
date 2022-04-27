package com.study.compose.ui.home.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopHeader(
    backdropRevealed: Boolean
) {
    val density = LocalDensity.current
    AnimatedContent(
        targetState = backdropRevealed,
        contentAlignment = Alignment.CenterStart,
        transitionSpec = {
            if (targetState) {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 240,
                        delayMillis = 120,
                        easing = LinearEasing
                    )
                ) + slideInHorizontally(
                    initialOffsetX = { with(density) { 30.dp.roundToPx() } },
                    animationSpec = tween(durationMillis = 270, easing = LinearEasing)
                ) with
                        fadeOut(
                            animationSpec = tween(
                                durationMillis = 120,
                                easing = LinearEasing
                            )
                        ) + slideOutHorizontally(
                    targetOffsetX = { with(density) { (-30).dp.roundToPx() } },
                    animationSpec = tween(durationMillis = 120, easing = LinearEasing)
                )
            } else {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 180,
                        delayMillis = 90,
                        easing = LinearEasing
                    )
                ) + slideInHorizontally(
                    initialOffsetX = { with(density) { (-30).dp.roundToPx() } },
                    animationSpec = tween(durationMillis = 270, easing = LinearEasing)
                ) with
                        fadeOut(
                            animationSpec = tween(
                                durationMillis = 90,
                                easing = LinearEasing
                            )
                        ) +
                        slideOutHorizontally(
                            targetOffsetX = { with(density) { 30.dp.roundToPx() } },
                            animationSpec = tween(durationMillis = 90, easing = LinearEasing)
                        )
            }.using(SizeTransform(clip = false))
        }
    ) { revealed ->
        if (revealed) {
            TopHeaderSearch()
        } else {
            TopHeaderText()
        }
    }

}

@Preview
@Composable
fun TopHeaderPreview() {
    ShrineComposeTheme {
        Column(Modifier.fillMaxWidth()) {
            TopHeader(false)
        }
    }
}

@Composable
fun TopHeaderText() {
    Text(
        text = stringResource(id = com.study.compose.ui.common.R.string.txt_shrine),
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TopHeaderSearch() {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .height(56.dp)
            .padding(end = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = searchText, onValueChange = { searchText = it }, singleLine = true,
        )

        if (searchText.isEmpty()) {
            TopHeaderText()
        }
        Divider(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    }
}

@Preview
@Composable
fun TopHeaderSearchPreview() {
    ShrineComposeTheme {
        TopHeaderSearch()
    }
}

@Composable
fun NavigationIcon(
    backdropRevealed: Boolean,
    onRevealed: (Boolean) -> Unit = {}
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxHeight()
            .toggleable(
                value = backdropRevealed,
                onValueChange = { onRevealed(it) },
                indication = rememberRipple(bounded = false, radius = 64.dp),
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = !backdropRevealed,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 180,
                    delayMillis = 90,
                    easing = LinearEasing
                )
            ) + slideInHorizontally(
                initialOffsetX = { with(density) { (-20).dp.roundToPx() } },
                animationSpec = tween(durationMillis = 270, easing = LinearEasing)
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 120,
                    easing = LinearEasing
                )
            ) + slideOutHorizontally(
                targetOffsetX = { with(density) { (-20).dp.roundToPx() } },
                animationSpec = tween(
                    durationMillis = 120,
                    easing = LinearEasing
                ),
            ),
            label = "Navigation Menu Icon"
        ) {
            Icon(
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_menu_cut_24px),
                contentDescription = "menu"
            )
        }

        val logoTransition =
            updateTransition(targetState = backdropRevealed, label = "logo transition")
        val logoOffset by logoTransition.animateDp(
            transitionSpec = {
                if (targetState) {
                    tween(durationMillis = 120, easing = LinearEasing)
                } else {
                    tween(durationMillis = 270, easing = LinearEasing)
                }
            },
            label = "logo offset"
        ) { backdropRevealed ->
            if (!backdropRevealed) 20.dp else 0.dp
        }

        Icon(
            painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_shrine_logo),
            contentDescription = "logo",
            modifier = Modifier.offset(x = logoOffset)
        )
    }

}

@Preview
@Composable
fun NavigationIconPreviews() {
    ShrineComposeTheme {
        Surface {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                NavigationIcon(false) {

                }
            }

        }

    }
}

@Composable
fun HomeActionIcon(
    @DrawableRes resId: Int,
    onPressed: () -> Unit = {},
    contentDescription: String? = null
) {
    Icon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .clickable { onPressed() }
            .padding(12.dp)
    )
}

