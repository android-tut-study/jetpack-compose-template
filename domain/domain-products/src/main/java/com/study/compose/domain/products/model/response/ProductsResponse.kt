package com.study.compose.domain.products.model.response

import com.squareup.moshi.Json

data class ProductsResponse(

    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "price") val price: Float,
    @Json(name = "description") val description: String,
    @Json(name = "category") val category: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "rating") val rating: ProductRatingResponse,
)
