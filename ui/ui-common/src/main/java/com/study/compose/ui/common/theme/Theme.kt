package com.study.compose.ui.common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

//private val DarkColorPalette = darkColors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200
//)
//
//private val LightColorPalette = lightColors(
//    primary = Purple500,
//    primaryVariant = Purple700,
//    secondary = Teal200
//
//    /* Other default colors to override
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//    */
//)

@Composable
fun ShrineComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = ShrineLightColorPalette,
        typography = ShrineTypography,
        shapes = ShrineShape,
        content = content
    )
}