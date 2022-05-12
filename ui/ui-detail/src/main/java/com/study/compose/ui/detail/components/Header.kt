package com.study.compose.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme

@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    onNavigationPressed: () -> Unit = {},
    onCartAddPressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DetailNavigationIcon(onPressed = onNavigationPressed)
        DetailHeaderActions(
            onCartAddPressed = onCartAddPressed,
            onFavoritePressed = onFavoritePressed
        )
    }
}

@Composable
fun DetailNavigationIcon(onPressed: () -> Unit) {
    IconButton(
        onClick = { onPressed() },
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
    ) {
        Icon(
            painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_close_24),
            contentDescription = "detail navigation",
            modifier = Modifier.padding(8.dp)
        )
    }

}

@Composable
fun DetailHeaderActions(onCartAddPressed: () -> Unit, onFavoritePressed: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        IconButton(
            onClick = { onCartAddPressed() },
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_add_cart_24),
                contentDescription = "addCart",
                modifier = Modifier.padding(8.dp)
            )
        }
        IconButton(
            onClick = { onFavoritePressed() },
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_favorite_border_24),
                contentDescription = "favorite",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun DetailHeaderPreview() {
    ShrineComposeTheme {
        DetailHeader()
    }
}