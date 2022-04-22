package com.study.compose.domain.products.model.response

import com.squareup.moshi.Json

data class ProductRatingResponse(
    @field:Json(name = "rate") val rate: Float,
    @field:Json(name = "count") val count: Int
)