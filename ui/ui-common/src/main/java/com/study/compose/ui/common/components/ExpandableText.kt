package com.study.compose.ui.common.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.study.compose.ui.common.theme.ShrineComposeTheme

const val DEFAULT_MINIMUM_TEXT_LINE = 3

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
) {
    if (text.isEmpty()) {
        return
    }
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Text(
        modifier = Modifier
            .clickable(clickable) {
                isExpanded = !isExpanded
            }
            .animateContentSize()
            .then(modifier),
        text = buildAnnotatedString {
            if (clickable) {
                if (isExpanded) {
                    append(text)
                    withStyle(style = showMoreStyle) { append(showLessText) }
                } else {
                    val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                        .dropLast(showMoreText.length)
                        .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                    append(adjustText)
                    withStyle(style = showLessStyle) { append(showMoreText) }
                }
            } else {
                append(text)
            }
        },
        maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
        fontStyle = fontStyle,
        onTextLayout = { textLayoutResult ->
            if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                clickable = true
                lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
            }
        },
        style = style,
    )

}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun ExpandableTextPreview() {
    ShrineComposeTheme {
        ExpandableText(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday. Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday .Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            style = MaterialTheme.typography.body2.copy(
                color = Color(
                    0xFF4D4C4C
                )
            ),
            fontStyle = FontStyle.Italic,
            collapsedMaxLine = 3
        )
    }
}