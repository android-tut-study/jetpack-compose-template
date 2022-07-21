package com.study.compose.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.android.core.qr.generateQrLogoBitmap
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.detail.data.ProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ProductSharing(modifier: Modifier = Modifier, productDetail: ProductDetail?) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val qrWidth = screenWidth * 0.7f

    val qrSize = with(LocalDensity.current) { qrWidth.roundToPx() }
    var qrBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    val valueColor = MaterialTheme.colors.onSurface.toArgb()
    val bgColor = MaterialTheme.colors.surface.toArgb()
    val logoColor = MaterialTheme.colors.primary.toArgb()
    val ctx = LocalContext.current
    val productLink = "Fake Product Link!!!" //TODO generate Product Link
    if (productDetail != null) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.Default) {
                val logoBitmap =
                    ContextCompat.getDrawable(ctx, com.study.compose.ui.common.R.drawable.logo)
                        ?.apply {
                            colorFilter =
                                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                    logoColor,
                                    BlendModeCompat.SRC_ATOP
                                )
                        }?.toBitmap()

                if (logoBitmap != null) {
                    qrBitmap = generateQrLogoBitmap(
                        productLink,
                        qrSize,
                        logoBitmap,
                        valueColor,
                        bgColor,
                    ).asImageBitmap()
                }
            }
        }
    }
    if (qrBitmap != null) {
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
                bitmap = qrBitmap!!,
                contentDescription = "Fake QR"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Qr Code")

        }
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