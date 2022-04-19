package com.study.compose.domain.products.model.response

import com.squareup.moshi.Json

data class ProductRatingResponse(
    @Json(name = "rate") val rate: Float,
    @Json(name = "count") val count: Int
)