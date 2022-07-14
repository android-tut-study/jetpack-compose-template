package com.study.compose.domain.products.mapper

import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.ProductRatingDomain
import com.study.compose.domain.products.model.response.ProductRatingResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatingResponseToDomainMapper @Inject constructor() :
    Mapper<ProductRatingResponse, ProductRatingDomain> {
    override fun invoke(response: ProductRatingResponse): ProductRatingDomain {
        return ProductRatingDomain(
            count = response.count,
            rate = response.rate
        )
    }
}