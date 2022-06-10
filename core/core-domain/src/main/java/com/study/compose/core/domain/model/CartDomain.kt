package com.study.compose.core.domain.model

import android.graphics.Color

data class CartDomain(
    val id: Long? = null,
    val productId: Long,
    val title: String,
    val description: String,
    val price: Float,
    val amount: Int,
    val color: Int = Color.CYAN,
    val size: Int = 1,
    val category: String,
    val imageUrl: String
) {

    companion object {
        fun fromProduct(productDomain: ProductDomain, amount: Int) = CartDomain(
            productId = productDomain.id,
            title = productDomain.title,
            description = productDomain.description,
            price = productDomain.price,
            amount = amount,
            imageUrl = productDomain.imageUrl,
            category = productDomain.category,
            // TODO update size, color
            size = 1,
        )
    }
}

enum class CartChangeType {
    INSERT,
    REMOVE,
    UPDATE,
    NONE
}