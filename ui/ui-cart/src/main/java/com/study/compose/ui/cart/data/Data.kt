package com.study.compose.ui.cart.data

import android.graphics.Color
import com.study.compose.core.domain.model.CartDomain

data class Cart(
    val productId: Long,
    val title: String = "",
    val description: String = "",
    val price: Float,
    val category: String = "",
    val imageUrl: String = "",
    val color: Int = Color.CYAN,
    val size: Int = 1
) {

    constructor(cartDomain: CartDomain) : this(
        productId = cartDomain.productId,
        title = cartDomain.title,
        description = cartDomain.description,
        price = cartDomain.price,
        category = "category",
        imageUrl = cartDomain.imageUrl,
    )
}
