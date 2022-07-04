package com.study.compose.domain.products.model.response

import com.squareup.moshi.Json

data class ProductsResponse(

    @field:Json(name = "id") val id: Long,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "price") val price: Float,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "category") val category: String,
    @field:Json(name = "image") val imageUrl: String,
    @field:Json(name = "rating") val rating: ProductRatingResponse,
)
