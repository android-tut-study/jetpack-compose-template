package com.study.compose.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .height(56.dp).then(modifier),
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
    Box(modifier = Modifier
        .clickable { onPressed() }
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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_add_cart_24),
            contentDescription = "addCart",
            modifier = Modifier.clickable { onCartAddPressed() }
        )
        Icon(
            painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_favorite_border_24),
            contentDescription = "favorite",
            modifier = Modifier.clickable { onFavoritePressed() }
        )

    }
}

@Preview
@Composable
fun DetailHeaderPreview() {
    ShrineComposeTheme {
        DetailHeader()
    }
}