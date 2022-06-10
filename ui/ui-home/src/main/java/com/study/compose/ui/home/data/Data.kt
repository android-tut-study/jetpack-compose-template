package com.study.compose.ui.home.data

import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.ui.home.R

sealed class HomeModel

data class HomeProduct constructor (var products: List<Product>) : HomeModel() {

    companion object {
        fun generateFromDomain(domain: List<ProductDomain>) = HomeProduct(products = domain.map(::Product))
    }
}

data class Product(
    val id: Long,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageUrl: String
) : HomeModel() {
    constructor(productDomain: ProductDomain) : this(
        id = productDomain.id,
        title = productDomain.title,
        price = productDomain.price,
        description = productDomain.description,
        category = productDomain.category,
        imageUrl = productDomain.imageUrl
    )
}

enum class Category {
    ALL,
    ACCESSORIES,
    CLOTHING,
    HOME,
}