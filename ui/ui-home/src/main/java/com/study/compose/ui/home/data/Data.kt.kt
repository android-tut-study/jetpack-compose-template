package com.study.compose.ui.home.data

import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.ui.home.R

sealed interface HomeModel

data class HomeProduct constructor (var products: List<Product>) : HomeModel {

    companion object {
        fun generateFromDomain(domain: List<ProductDomain>) = HomeProduct(products = domain.map(::Product))
    }
}

data class Product(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageUrl: String
) : HomeModel {
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

enum class Vendor {
    Alphi,
    Lmbrjk,
    Mal,
    Six,
    Squiggle,
}

data class Cart(
    val id: Int,
    val title: String,
    val price: Int,
    val vendor: Vendor,
    val photoResId: Int
)

val SampleCartItems = listOf(
    Cart(
        id = 0,
        title = "Vagabond sack",
        price = 120,
        vendor = Vendor.Squiggle,
        photoResId = R.drawable.photo_0
    ),
    Cart(
        id = 1,
        title = "Stella sunglasses",
        price = 58,
        vendor = Vendor.Mal,
        photoResId = R.drawable.photo_1
    ),
    Cart(
        id = 0,
        title = "Vagabond sack",
        price = 120,
        vendor = Vendor.Squiggle,
        photoResId = com.study.compose.ui.common.R.drawable.fake1
    ),
    Cart(
        id = 1,
        title = "Stella sunglasses",
        price = 58,
        vendor = Vendor.Mal,
        photoResId = R.drawable.photo_1
    ),
    Cart(
        id = 0,
        title = "Vagabond sack",
        price = 120,
        vendor = Vendor.Squiggle,
        photoResId = com.study.compose.ui.common.R.drawable.fake1
    ),
    Cart(
        id = 2,
        title = "Whitney belt",
        price = 35,
        vendor = Vendor.Lmbrjk,
        photoResId = R.drawable.photo_2
    ),
    Cart(
        id = 3,
        title = "Garden stand",
        price = 98,
        vendor = Vendor.Alphi,
        photoResId = R.drawable.photo_3
    ),
    Cart(
        id = 4,
        title = "Strut earrings",
        price = 34,
        vendor = Vendor.Six,
        photoResId = R.drawable.photo_4
    ),
    Cart(
        id = 5,
        title = "Varsity socks",
        price = 12,
        vendor = Vendor.Lmbrjk,
        photoResId = R.drawable.photo_5
    ),
    Cart(
        id = 6,
        title = "Weave key ring",
        price = 16,
        vendor = Vendor.Six,
        photoResId = R.drawable.photo_6
    ),
    Cart(
        id = 7,
        title = "Gatsby hat",
        price = 40,
        vendor = Vendor.Six,
        photoResId = R.drawable.photo_7
    ),
    Cart(
        id = 8,
        title = "Shrug bag",
        price = 198,
        vendor = Vendor.Squiggle,
        photoResId = R.drawable.photo_8
    ),
    Cart(
        id = 9,
        title = "Gilt desk trio",
        price = 58,
        vendor = Vendor.Alphi,
        photoResId = R.drawable.photo_9
    ),
)