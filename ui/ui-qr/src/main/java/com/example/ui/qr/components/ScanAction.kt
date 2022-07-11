package com.example.ui.qr.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ui.qr.R

@Composable
fun ScanAction(
    modifier: Modifier = Modifier,
    flashSupported: Boolean = true, // TODO check Flash Support
    onImageSearched: () -> Unit,
    onFlashPressed: () -> Unit
) {
    val iconModifier = Modifier.size(36.dp)
    Row(
        modifier = Modifier
            .padding(20.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onImageSearched) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(id = R.drawable.ic_image_search),
                contentDescription = "Image Searcher"
            )
        }
        IconButton(
            onClick = onFlashPressed,
            enabled = flashSupported
        ) {
            Icon(
                modifier = iconModifier,
                painter = painterResource(id = R.drawable.ic_flashlight),
                contentDescription = "Flash Light"
            )
        }
    }

}