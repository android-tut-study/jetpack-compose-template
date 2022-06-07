package com.study.compose.ui.cart.data

data class Cart(
    val id: Int,
    val title: String = "",
    val description: String = "",
    val price: Float,
    val category: String = "",
    val imageUrl: String = "",

    //fake
    val vendor: Vendor,
    val photoResId: Int
)
