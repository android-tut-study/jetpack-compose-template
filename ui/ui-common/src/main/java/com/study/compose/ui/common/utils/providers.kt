package com.study.compose.ui.common.utils

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color

fun textSelectionColors(color: Color) = TextSelectionColors(
    handleColor = color,
    backgroundColor = color.copy(alpha = 0.4f)
)