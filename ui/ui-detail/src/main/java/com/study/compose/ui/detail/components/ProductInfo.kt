package com.study.compose.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductInfo(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .then(modifier),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "LMBRJK")
                Text(text = "$240", modifier = Modifier.padding(end = 8.dp))
            }
            Text(text = "Flow Shirt Blouse", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "This is Description \n ......", style = MaterialTheme.typography.body2.copy(
                    color = Color(
                        0xFF4D4C4C
                    )
                ), fontStyle = FontStyle.Italic
            )
        }
    }
}

@Preview
@Composable
fun ProductInfoPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        ProductInfo()
    }
}