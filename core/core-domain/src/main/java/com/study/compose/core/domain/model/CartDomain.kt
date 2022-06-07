package com.study.compose.core.domain.model

data class CartDomain(
    val productId: Int,
    val title: String,
    val description: String,
    val price: Float,
    val amount: Float,
)