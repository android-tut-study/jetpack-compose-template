package com.study.compose.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.study.compose.ui.common.R
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.data.ProductDetail

val defaultImageSize = 36.dp

@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    productDetail: ProductDetail?,
    productImageModifier: Modifier = Modifier,
    onNavigationPressed: () -> Unit = {},
    onCartAddPressed: () -> Unit = {},
    onFavoritePressed: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            DetailNavigationIcon(onPressed = onNavigationPressed)
            DetailProductImage(productDetail = productDetail, modifier = productImageModifier)
        }
        DetailHeaderActions(
            onCartAddPressed = onCartAddPressed,
            onFavoritePressed = onFavoritePressed
        )
    }
}

@Composable
fun DetailNavigationIcon(onPressed: () -> Unit) {
    Row {
        IconButton(
            onClick = { onPressed() },
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close_24),
                contentDescription = "detail navigation",
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}

@Composable
fun DetailProductImage(modifier: Modifier = Modifier, productDetail: ProductDetail?) {
    val titleImageModifier = Modifier
        .size(defaultImageSize)
        .then(modifier)
    if (productDetail != null) {
        AsyncImage(
            modifier = titleImageModifier,
            model = productDetail.imageUrl,
            contentDescription = "Fake2",
            contentScale = ContentScale.Inside,
        )
    } else {
        // TODO Change to PlaceHolder
        Image(
            modifier = titleImageModifier,
            painter = painterResource(id = R.drawable.fake),
            contentDescription = "Fake2",
            contentScale = ContentScale.Inside,
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
                painter = painterResource(id = R.drawable.ic_add_cart_24),
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
                painter = painterResource(id = R.drawable.ic_favorite_border_24),
                contentDescription = "favorite",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DetailHeaderPreview() {
    ShrineComposeTheme {
        DetailHeader(productDetail = ProductDetail(
            id = 1,
            title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            price= 109.95f,
            description ="Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            category = "men's clothing",
            imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
        ))
    }
}