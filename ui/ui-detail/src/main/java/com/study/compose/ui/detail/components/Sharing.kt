package com.study.compose.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.data.ProductDetail

@Composable
fun ProductSharing(modifier: Modifier = Modifier, productDetail: ProductDetail) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val qrWidth = screenWidth * 0.7f
    Column(
        modifier = Modifier
            .padding(20.dp)
            .width(screenWidth)
            .wrapContentHeight()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(qrWidth),
            painter = painterResource(id = com.study.compose.ui.common.R.drawable.fake),
            contentDescription = "Fake QR"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Qr Code")
    }
}

@Preview(widthDp = 480, heightDp = 640)
@Composable
fun ProductSharingPreview() {
    ShrineComposeTheme {
        ProductSharing(
            productDetail = ProductDetail(
                id = 1,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = 109.95f,
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                category = "men's clothing",
                imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            )
        )
    }
}