package com.study.compose.ui.detail.data

import com.study.compose.core.domain.model.ProductDomain

sealed class DetailModel

data class ProductDetail(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageUrl: String
) : DetailModel() {
    constructor(productDomain: ProductDomain) : this(
        id = productDomain.id,
        title = productDomain.title,
        price = productDomain.price,
        description = productDomain.description,
        category = productDomain.category,
        imageUrl = productDomain.imageUrl
    )
}