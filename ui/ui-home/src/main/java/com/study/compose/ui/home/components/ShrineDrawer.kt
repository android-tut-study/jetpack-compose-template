package com.study.compose.ui.home.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.R

const val MENU_ALL = "ALL"

@Composable
fun ShrineDrawer(
    modifier: Modifier = Modifier,
    backdropRevealed: Boolean,
    currentCategory: String = MENU_ALL,
    categories: List<String> = emptyList(),
    onMenuSelected: (category: String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = backdropRevealed,
            enter = EnterTransition.None,
            exit = ExitTransition.None,
            label = "Navigation menu"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEachIndexed { index, category ->
                    val selected = currentCategory == category
                    DrawerMenu(
                        index = index,
                        modifier = Modifier.clickable(!selected) {
                            Log.e("ANNX", "On Menu Select $category")
                            onMenuSelected(category)
                        }
                    ) {
                        MenuText(text = category, selected = selected)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MenuText(
    text: String,
    selected: Boolean,
) {
    Box(
        modifier = Modifier.height(44.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = selected,
            enter = scaleIn(animationSpec = tween(200)) + fadeIn(initialAlpha = 0.3f),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu_selected_highlight),
                contentDescription = "Menu Selected Icon"
            )
        }
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityScope.DrawerMenu(
    modifier: Modifier = Modifier,
    index: Int,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .animateEnterExit(
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 240,
                        delayMillis = index * 15 + 60,
                        easing = LinearEasing
                    )
                ),
                exit = fadeOut(animationSpec = tween(durationMillis = 90, easing = LinearEasing)),
                label = "item $index"
            )
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ShrineDrawerPreview() {
    ShrineComposeTheme {
        ShrineDrawer(backdropRevealed = false, currentCategory = MENU_ALL)
    }
}
