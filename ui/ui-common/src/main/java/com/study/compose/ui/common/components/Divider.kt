package com.study.compose.ui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme


@Composable
fun ShrineDivider(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFBDBDBD),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}

@Preview
@Composable
private fun ShrineDividerPreview() {
    ShrineComposeTheme {
        androidx.compose.material.Surface {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(10.dp)) {
                ShrineDivider(Modifier.align(Alignment.Center))
            }
        }
    }
}