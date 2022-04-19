package com.study.compose.core.domain.model

data class ProductDomain(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: ProductRatingDomain
)