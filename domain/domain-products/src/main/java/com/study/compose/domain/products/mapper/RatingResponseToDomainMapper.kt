package com.study.compose.domain.products.mapper

import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.ProductRatingDomain
import com.study.compose.domain.products.model.response.ProductRatingResponse

class RatingResponseToDomainMapper: Mapper<ProductRatingResponse, ProductRatingDomain> {
    override fun invoke(response: ProductRatingResponse): ProductRatingDomain {
        return ProductRatingDomain(
            count = response.count,
            rate = response.rate
        )
    }
}