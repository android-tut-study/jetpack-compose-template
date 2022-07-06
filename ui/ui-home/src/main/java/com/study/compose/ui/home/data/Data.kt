package com.study.compose.ui.home.data

import android.graphics.Color
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.core.domain.model.ProductDomain

sealed class HomeModel

data class HomeProduct constructor (var products: List<Product>, val filteredProducts: List<Product> = products.toMutableList()) : HomeModel() {

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
    val imageUrl: String,
    val isAdded: Boolean = false
) : HomeModel() {
    constructor(productDomain: ProductDomain) : this(
        id = productDomain.id,
        title = productDomain.title,
        price = productDomain.price,
        description = productDomain.description,
        category = productDomain.category,
        imageUrl = productDomain.imageUrl
    )

    fun toCart() = CartDomain(
        productId = this.id,
        title=this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        imageUrl = this.imageUrl,
        amount = 1,
        color = Color.CYAN
    )
}

enum class Category {
    ALL,
    ACCESSORIES,
    CLOTHING,
    HOME,
}