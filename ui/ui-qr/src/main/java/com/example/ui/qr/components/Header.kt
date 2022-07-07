package com.example.ui.qr.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Header(modifier: Modifier = Modifier, onClosed: () -> Unit = {}) {
    IconButton(
        onClick = { onClosed() },
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .then(modifier)
    ) {
        Icon(
            painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_close_24),
            contentDescription = "Qr Close Button",
            modifier = Modifier.padding(8.dp)
        )
    }
}