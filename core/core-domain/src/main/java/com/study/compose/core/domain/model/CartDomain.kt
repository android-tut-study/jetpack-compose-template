package com.study.compose.core.domain.model

data class CartDomain(
    val productId: Int,
    val title: String,
    val description: String,
    val price: Float,
    val amount: Int,
) {

    companion object {
        fun fromProduct(productDomain: ProductDomain, amount: Int) = CartDomain(
            productId = productDomain.id,
            title = productDomain.title,
            description = productDomain.description,
            price = productDomain.price,
            amount = amount
        )
    }
}

enum class CartChangeType {
    INSERT,
    REMOVE,
    UPDATE,
    NONE
}