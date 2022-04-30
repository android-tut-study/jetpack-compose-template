package com.study.compose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.components.DetailHeader
import com.study.compose.ui.detail.components.MoreDetail
import com.study.compose.ui.detail.components.ProductInfo

private val MinTitleOffset = 56.dp
private val ExpandedImageHeight = 200.dp
private val CollapsedImageHeight = 80.dp

@Composable
fun Detail() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.surface) {
            val scrollState = rememberScrollState(0)
            Box(modifier = Modifier.fillMaxSize()) {
                ProductImage()
                DetailHeader()
                Body(scrollState)
            }
        }
    }
}

@Composable
fun Body(scrollState: ScrollState) {
    Column(Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(ExpandedImageHeight))
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Spacer(modifier = Modifier.height(10.dp))
            ProductInfo()
            MoreDetail(onSizeSelected = {}, onColorSelected = {})
            Spacer(modifier = Modifier.height(10.dp))
            AddToCart()
        }

    }
}

@Composable
fun ProductImage() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(ExpandedImageHeight), color = MaterialTheme.colors.secondary
    ) {
        Image(painter = painterResource(id = R.drawable.fake), contentDescription = "Fake")
    }
}

@Composable
fun AddToCart() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(56.dp),
        elevation = ButtonDefaults.elevation(4.dp),
        shape = CutCornerShape(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = com.study.compose.ui.common.R.drawable.ic_add_cart_24),
                contentDescription = "AddToCart"
            )
            Text(text = "Add To Cart".uppercase())
        }
    }
}


@Preview
@Composable
private fun DetailPreview() {
    Detail()
}