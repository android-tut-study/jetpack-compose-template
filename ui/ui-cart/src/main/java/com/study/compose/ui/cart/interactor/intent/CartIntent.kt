package com.study.compose.ui.cart.interactor.intent

sealed class CartIntent {
    object AllCarts: CartIntent()
    object ListenCartChange: CartIntent()
}