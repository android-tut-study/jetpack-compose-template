package com.study.compose.ui.cart.interactor.intent

sealed class CartIntent {
    object Initial: CartIntent()
    object AllCarts: CartIntent()
}