package com.study.compose.ui.common.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

fun String.toUrlStyleAnnotatedString(
    style: SpanStyle = SpanStyle(
        color = Color.Blue,
        fontStyle = FontStyle.Italic,
    )
) = buildAnnotatedString {
    withStyle(
        SpanStyle(
            color = Color.Blue,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline
        ) + style
    ) { append(this@toUrlStyleAnnotatedString) }
}