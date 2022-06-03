package com.study.compose.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.components.ExpandableText
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.data.ProductDetail

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ProductInfo(modifier: Modifier = Modifier, productDetail: ProductDetail?) {
    Surface(color = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .then(modifier),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = productDetail?.category.orEmpty())
                Text(
                    text = "$${productDetail?.price ?: "00"} ",
                    style = MaterialTheme.typography.h4.copy(
                        fontSize = TextUnit(
                            24f,
                            TextUnitType.Sp
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = productDetail?.title.orEmpty(),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight(500)
            )
        }
    }
}

@Preview
@Composable
private fun ProductInfoPreview() {
    ShrineComposeTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            ProductInfo(
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
}