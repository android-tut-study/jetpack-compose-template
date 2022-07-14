package com.study.compose.ui.detail.interactor.intent

import com.study.compose.ui.detail.data.ProductDetail

sealed class DetailIntent {
    data class Initial(val productId: Long) : DetailIntent()
    data class AddCart(val product: ProductDetail, val amount: Int) : DetailIntent()
    object ClearCartAddedFlag : DetailIntent()
}