package com.study.compose.ui.cart.data

import com.study.compose.ui.common.R

enum class Vendor {
    Alphi,
    Lmbrjk,
    Mal,
    Six,
    Squiggle,
}

val SampleCartItems = listOf(
    Cart(
        id = 0,
        title = "Vagabond sack",
        price = 120f,
        vendor = Vendor.Squiggle,
        photoResId = R.drawable.fake1
    ),
    Cart(
        id = 1,
        title = "Stella sunglasses",
        price = 58f,
        vendor = Vendor.Mal,
        photoResId = R.drawable.fake1
    ),
    Cart(
        id = 0,
        title = "Vagabond sack",
        price = 120f,
        vendor = Vendor.Squiggle,
        photoResId = R.drawable.fake1
    )
)