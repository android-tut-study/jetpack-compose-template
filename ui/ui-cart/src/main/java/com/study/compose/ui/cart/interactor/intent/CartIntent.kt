package com.study.compose.ui.cart.interactor.intent

sealed class CartIntent {
    object ListenCartChange: CartIntent()
}